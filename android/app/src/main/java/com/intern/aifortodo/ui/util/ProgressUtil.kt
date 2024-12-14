

package com.intern.aifortodo.ui.util

import androidx.annotation.IntRange
import com.intern.aifortodo.domain.model.Task
import com.intern.aifortodo.domain.model.TaskState

object ProgressUtil {

    fun getTasksDoneProgress(list: List<Task?>): Int =
        list.takeUnless { it.isEmpty() }?.let {
            (it.filter { task -> task?.state == TaskState.DONE }.size / it.size.toDouble() * MAX_PROGRESS).toInt()
        } ?: 0

    fun getPercentage(@IntRange(from = 0, to = 100) progress: Int) =
        progress.takeIf { it in 0..MAX_PROGRESS }?.let { "$it%" } ?: "-%"

    private const val MAX_PROGRESS = 100
}
