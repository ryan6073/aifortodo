package com.intern.aifortodo.ui.util

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.intern.aifortodo.data.database.dao.TaskDao
import com.intern.aifortodo.data.database.entity.TaskEntity
import com.intern.aifortodo.domain.model.TaskState

class TaskReminderWorker(
    context: Context,
    params: WorkerParameters,
    private val taskDao: TaskDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            // 使用 Flow 收集任务
            taskDao.getTasks().collect { tasks ->
                Log.d("TaskReminderWorker", "Loaded tasks: $tasks")

                val pendingTasks = tasks.filter { it.state == TaskState.DOING || it.state == TaskState.DONE }

                if (pendingTasks.isNotEmpty()) {
                    // 构建消息
                    val message = buildTaskMessage(pendingTasks)

                    // 发送通知
                    NotificationHelper.showNotification(applicationContext, message)

                    // 执行震动
                    NotificationHelper.startVibration(applicationContext)
                }
            }

            return Result.success()
        } catch (e: Exception) {
            Log.e("TaskReminderWorker", "Error in doWork: ${e.message}")
            return Result.failure()
        }
    }

    private fun buildTaskMessage(pendingTasks: List<TaskEntity>): String {
        return when {
            pendingTasks.size == 1 -> {
                val task = pendingTasks[0]
                "您有一个待办任务：${task.name}" +
                        (task.tag?.let { " (${it.name})" } ?: "")
            }
            else -> {
                val tasksByTag = pendingTasks.groupBy { it.tag }
                buildString {
                    append("您还有 ${pendingTasks.size} 个待办任务：\n")
                    tasksByTag.forEach { (tag, tasksForTag) ->
                        if (tag != null) {
                            append("${tag.name}: ${tasksForTag.size}个任务\n")
                        } else {
                            append("未分类: ${tasksForTag.size}个任务\n")
                        }
                    }
                }.trimEnd()
            }
        }
    }
}