package com.intern.aifortodo.ui.util
// TaskReminderWorkerFactory.kt

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.intern.aifortodo.data.database.dao.TaskDao
import javax.inject.Inject

class TaskReminderWorkerFactory @Inject constructor(
    private val taskDao: TaskDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            TaskReminderWorker::class.java.name ->
                TaskReminderWorker(appContext, workerParameters, taskDao)
            else -> null
        }
    }
}
