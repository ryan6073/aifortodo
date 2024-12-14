

package com.intern.aifortodo.data.database

import com.intern.aifortodo.domain.model.Tag
import com.intern.aifortodo.domain.model.TaskState
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    @Test
    fun `Tag to String`() {
        assertEquals(Tag.RED.name, Converters().toString(Tag.RED))
    }

    @Test
    fun `String to Tag`() {
        assertEquals(Tag.RED, Converters().toTag(Tag.RED.name))
    }

    @Test
    fun `String null returns default OTHER Tag`() {
        assertEquals(Tag.GRAY, Converters().toTag(null))
    }

    @Test
    fun `TaskState to String`() {
        assertEquals(TaskState.DONE.name, Converters().toString(TaskState.DONE))
    }

    @Test
    fun `String to TaskState`() {
        assertEquals(TaskState.DOING, Converters().toTaskState(TaskState.DOING.name))
    }

    @Test
    fun `String null returns default TaskState DOING`() {
        assertEquals(TaskState.DOING, Converters().toTaskState(null))
    }
}
