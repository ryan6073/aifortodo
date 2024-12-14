package com.intern.aifortodo.extensions


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.intern.aifortodo.ui.util.NotificationHelper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "VIBRATION_ACTION" -> {
                NotificationHelper.startVibration(context)
            }
            "NOTIFICATION_ACTION" -> {
                val message = intent.getStringExtra("message") ?: "提醒时间到了！"
                NotificationHelper.showNotification(context, message)
            }
        }
    }
}
