

package com.intern.aifortodo.ui.util

import com.intern.aifortodo.domain.model.Tag
import com.intern.aifortodo.domain.model.Task
import com.intern.aifortodo.domain.model.TaskState
import com.intern.aifortodo.ui.util.ProgressUtil.getTasksDoneProgress
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressUtilTest {

    @Test
    fun `Test getTasksDoneProgress`() {
        val tasks = arrayListOf<Task>()
        assertEquals(0, getTasksDoneProgress(tasks))
        tasks.add(Task(1, "", "", TaskState.DONE, 2, Tag.GRAY))
        assertEquals(100, getTasksDoneProgress(tasks))
        tasks.add(Task(2, "", "", TaskState.DOING, 2, Tag.GRAY))
        assertEquals(50, getTasksDoneProgress(tasks))
        tasks.add(
            Task(2, "", "", TaskState.DOING, 2, Tag.GRAY)
        )
        assertEquals(33, getTasksDoneProgress(tasks))
    }

    @Test
    fun `getPercentage for a progress value between 0 and 100`() {
        var percentage = ProgressUtil.getPercentage(50)
        assertEquals("50%", percentage)
        percentage = ProgressUtil.getPercentage(0)
        assertEquals("0%", percentage)
        percentage = ProgressUtil.getPercentage(100)
        assertEquals("100%", percentage)
    }

    @Test
    fun `getPercentage for a progress value less than 0`() {
        val percentage = ProgressUtil.getPercentage(-1)
        assertEquals("-%", percentage)
    }

    @Test
    fun `getPercentage for a progress value greater than 100`() {
        val percentage = ProgressUtil.getPercentage(105)
        assertEquals("-%", percentage)
    }
}
