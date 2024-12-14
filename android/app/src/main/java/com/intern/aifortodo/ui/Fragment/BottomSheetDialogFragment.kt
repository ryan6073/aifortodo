package com.intern.aifortodo.ui.Fragment

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.intern.aifortodo.data.database.dao.TaskDao
import com.intern.aifortodo.data.database.entity.TaskEntity
import com.intern.aifortodo.databinding.FragmentBottomSheetDialogListDialogBinding
import com.intern.aifortodo.domain.model.TaskState
import com.intern.aifortodo.ui.Message.Message
import com.intern.aifortodo.ui.adapter.ChatAdapter
import com.intern.aifortodo.ui.ai.AiResponse
import com.intern.aifortodo.ui.ai.QueryRequest
import com.intern.aifortodo.ui.ai.RetrofitInstance
import com.intern.aifortodo.ui.util.NotificationHelper
import com.intern.aifortodo.ui.util.TaskReminderWorkerFactory
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class MyBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDialogListDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatAdapter: ChatAdapter

    // 注入 TaskDao
    @Inject
    lateinit var taskDao: TaskDao

    @Inject
    lateinit var workerFactory: TaskReminderWorkerFactory

//    private fun scheduleTaskReminder() {
//        // 创建工作请求的约束条件
//        val constraints = Constraints.Builder()
//            .setRequiresBatteryNotLow(true) // 电池电量不低时运行
//            .build()
//
//        // 创建周期性工作请求
//        val periodicWorkRequest = PeriodicWorkRequestBuilder<TaskReminderWorker>(
//            15, TimeUnit.MINUTES, // 每15分钟执行一次
//            5, TimeUnit.MINUTES  // 灵活时间窗口
//        )
//            .setConstraints(constraints)
//            .build()
//
//        // 使用 WorkManager 调度任务
//        WorkManager.getInstance(requireContext())
//            .enqueueUniquePeriodicWork(
//                "TaskReminder",
//                ExistingPeriodicWorkPolicy.REPLACE,
//                periodicWorkRequest
//            )
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 请求通知权限（Android 13+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                100
            )
        }

        // 初始化 RecyclerView
        setupRecyclerView()



        // 在这里获取任务数据
        loadTasks()

        var currentTasks = listOf<TaskEntity>() // 用于存储最新的任务列表

        lifecycleScope.launch {
            getTasks().collect { tasks ->
                currentTasks = tasks // 更新当前任务列表
            }
        }

        // 设置发送按钮点击事件
        binding.sendButton.setOnClickListener {
            val message = binding.inputField.text.toString()
            if (message.isNotEmpty()) {
                // 添加用户消息
                chatAdapter.addMessage(Message(message, false))

                // 使用当前任务列表和用户输入
                lifecycleScope.launch {
                    sendQueryToAi(currentTasks, message)
                }

                // 清空输入框
                binding.inputField.text.clear()

                // 滚动到最新消息
                binding.recyclerView.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    private fun loadTasks() {
        lifecycleScope.launch {
            taskDao.getTasks().collect { tasks ->
                getSummary(tasks, "总结我今天的计划")
                // 添加欢迎消息
                chatAdapter.addMessage(Message("我是AI for Todo，请问有什么可以帮你的吗", true))

                context?.let { context ->
                    try {

//                        // 设置1分钟后的提醒
//                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"))
//                        calendar.add(Calendar.MINUTE, 1)
//                        val timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
//                            timeZone = TimeZone.getTimeZone("Asia/Shanghai")
//                        }.format(calendar.time)
//
//                        // 设置定时震动
//                        NotificationHelper.scheduleVibration(context, timestamp)
//
//                        // 设置定时通知
//                        NotificationHelper.scheduleNotification(context, timestamp, "该完成任务了！")

                    } catch (e: SecurityException) {
                        // 处理权限错误
                        Toast.makeText(context, "需要精确闹钟权限才能设置提醒", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun getTasks(): Flow<List<TaskEntity>> {
        return taskDao.getTasks()
    }

    private fun handleAiResponse(response: AiResponse) {
        // 首先添加消息到聊天界面


        when (response.tool) {
            "default", "summary" -> {
                // 默认聊天和概括模式，无需额外处理
                chatAdapter.addMessage(Message(response.response, true))
            }

            "reminder", "alarm" -> {
                val jsonObject = JSONObject(response.response)
                val content = jsonObject.getString("content")
                chatAdapter.addMessage(Message(content, true))

                context?.let { context ->
                    try {
                        val responseData = JSONObject(response.response)
                        val timeStr = responseData.getString("time")
                        val content = responseData.getString("content")

                        // 转换时间格式
                        val inputFormat =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        inputFormat.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
                        val date = inputFormat.parse(timeStr)

                        val outputFormat =
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
                        val timestamp = outputFormat.format(date)

                        // 设置通知
                        NotificationHelper.scheduleNotification(context, timestamp, content)

                        // 如果是alarm类型，添加震动
                        if (response.tool == "alarm") {
                            NotificationHelper.scheduleVibration(context, timestamp)
                        }

                    } catch (e: SecurityException) {
                        // 处理权限错误
                        Toast.makeText(context, "需要精确闹钟权限才能设置提醒", Toast.LENGTH_LONG)
                            .show()
                    } catch (e: Exception) {
                        Log.e("HandleResponse", "Error handling response: ${e.message}", e)
                        Toast.makeText(context, "处理响应时出现错误", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    suspend fun sendQueryToAi(tasks: List<TaskEntity>, query: String) {
        val tasksJson = tasksToJson(tasks)
        val request = QueryRequest(tasks = tasksJson, query = query)
        try {
            val jsonObject = JsonObject().apply {
                addProperty("query", query)
                add("tasks", Gson().toJsonTree(tasks))
            }

            // 将 JSON 转换为 RequestBody
            val requestBody = jsonObject.toString()
                .toRequestBody("application/json".toMediaType())

            val response = RetrofitInstance.api.askQuestion(requestBody)
            // summary default reminder
            handleAiResponse(response)
            // 处理响应
        } catch (e: Exception) {
            Log.e("AI Response", "Error: ${e.message}", e)
            e.printStackTrace() // 打印完整的堆栈跟踪
        }
    }

    suspend fun getSummary(tasks: List<TaskEntity>, query: String) {
        val tasksJson = tasksToJson(tasks)
        val request = QueryRequest(tasks = tasksJson, query = query)
        try {
            val jsonObject = JsonObject().apply {
                addProperty("query", query)
                add("tasks", Gson().toJsonTree(tasks))
            }

            // 将 JSON 转换为 RequestBody
            val requestBody = jsonObject.toString()
                .toRequestBody("application/json".toMediaType())

            val response = RetrofitInstance.api.getSummary(requestBody)
            chatAdapter.addMessage(Message(response.response, true))
            // 处理响应
        } catch (e: Exception) {
            Log.e("AI Response", "Error: ${e.message}", e)
            e.printStackTrace() // 打印完整的堆栈跟踪
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true  // 从底部开始堆叠项目
            }
            adapter = chatAdapter
            // 添加item间距
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.top = 8
                    outRect.bottom = 8
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)

            // 设置展开高度为屏幕高度的 2/3
            val screenHeight = resources.displayMetrics.heightPixels
            val desiredHeight = (screenHeight * 0.66).toInt()

            bottomSheet.layoutParams.height = desiredHeight
            behavior.apply {
                peekHeight = desiredHeight  // 设置初始显示高度
                state = BottomSheetBehavior.STATE_EXPANDED  // 设置初始状态为展开
                isHideable = false  // 防止完全隐藏
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        NotificationHelper.stopPeriodicVibration()
        _binding = null
    }


    fun tasksToJson(tasks: List<TaskEntity>): String {
        val gson = Gson()
        return gson.toJson(tasks)
    }

}
