package com.intern.aifortodo.ui.util

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.intern.aifortodo.R
import com.intern.aifortodo.extensions.AlarmReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


object NotificationHelper {
    private const val CHANNEL_ID = "todo_list_notifications"
    private const val NOTIFICATION_ID = 1
    private const val VIBRATION_REQUEST_CODE = 100
    private const val NOTIFICATION_REQUEST_CODE = 101

    // 检查是否有设置精确闹钟的权限
    @RequiresApi(Build.VERSION_CODES.S)
    private fun hasExactAlarmPermission(context: Context): Boolean {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return alarmManager.canScheduleExactAlarms()
    }

    // 请求精确闹钟权限
    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestExactAlarmPermission(context: Context) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
            data = Uri.parse("package:${context.packageName}")
        }
        context.startActivity(intent)
    }

    // 使用东八区时间
    private val CHINA_TIMEZONE = TimeZone.getTimeZone("Asia/Shanghai")

    // 使用 timestamp 设置定时震动
    fun scheduleVibration(context: Context, timestamp: String) {
        try {
            // 将 timestamp 转换为毫秒时间，使用东八区
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            dateFormat.timeZone = CHINA_TIMEZONE
            val date = dateFormat.parse(timestamp)
            val triggerTimeMillis = date?.time ?: return

            // 检查时间是否已过期
            if (triggerTimeMillis <= System.currentTimeMillis()) {
                Log.d("NotificationHelper", "指定时间已过期")
                return
            }

            // Android 12 及以上版本需要检查权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!hasExactAlarmPermission(context)) {
                    requestExactAlarmPermission(context)
                    return
                }
            }

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                action = "VIBRATION_ACTION"
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                VIBRATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
                Log.d("NotificationHelper", "震动已设置，将在 ${dateFormat.format(Date(triggerTimeMillis))} 触发")
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
            }
        } catch (e: Exception) {
            Log.e("NotificationHelper", "设置震动失败", e)
            e.printStackTrace()
        }
    }

    // 使用 timestamp 设置定时通知
    fun scheduleNotification(context: Context, timestamp: String, message: String) {
        try {
            // 将 timestamp 转换为毫秒时间，使用东八区
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            dateFormat.timeZone = CHINA_TIMEZONE
            val date = dateFormat.parse(timestamp)
            val triggerTimeMillis = date?.time ?: return

            // 检查时间是否已过期
            if (triggerTimeMillis <= System.currentTimeMillis()) {
                Log.d("NotificationHelper", "指定时间已过期")
                return
            }

            // Android 12 及以上版本需要检查权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!hasExactAlarmPermission(context)) {
                    requestExactAlarmPermission(context)
                    return
                }
            }

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                action = "NOTIFICATION_ACTION"
                putExtra("message", message)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
                Log.d("NotificationHelper", "通知已设置，将在 ${dateFormat.format(Date(triggerTimeMillis))} 触发")
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
            }
        } catch (e: Exception) {
            Log.e("NotificationHelper", "设置通知失败", e)
            e.printStackTrace()
        }
    }

    // 提供一个辅助方法来格式化当前时间（用于测试）
    fun getCurrentTimeString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = CHINA_TIMEZONE
        return dateFormat.format(Date())
    }

    // 取消定时震动
    fun cancelScheduledVibration(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "VIBRATION_ACTION"
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            VIBRATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    // 取消定时通知
    fun cancelScheduledNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "NOTIFICATION_ACTION"
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    // 立即开始震动
    fun startVibration(context: Context, duration: Long = 1000) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }

    // 定时震动的功能
    private var vibrationJob: Job? = null

    fun startPeriodicVibration(context: Context, intervalMillis: Long = 5000) {
        vibrationJob?.cancel() // 取消之前的震动任务

        vibrationJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                startVibration(context)
                delay(intervalMillis) // 等待指定时间间隔
            }
        }
    }

    fun stopPeriodicVibration() {
        vibrationJob?.cancel()
        vibrationJob = null
    }


    // 立即显示通知
    fun showNotification(context: Context, message: String) {
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("提示")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
