

package com.intern.aifortodo.data.database.mapper

import com.intern.aifortodo.data.database.entity.TaskEntity
import com.intern.aifortodo.data.database.mapper.TaskMapper.toDomain
import com.intern.aifortodo.data.database.mapper.TaskMapper.toEntity
import com.intern.aifortodo.domain.model.Tag
import com.intern.aifortodo.domain.model.Task
import com.intern.aifortodo.domain.model.TaskState
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskMapperTest {

    @Test
    fun `Task to TaskEntity`() {
        val task = Task(1, "Name", "Description", TaskState.DOING, 2, Tag.GRAY)
        val taskEntity = task.toEntity()
        assertEquals(task.id, taskEntity?.id)
        assertEquals(task.name, taskEntity?.name)
        assertEquals(task.description, taskEntity?.description)
        assertEquals(task.state, taskEntity?.state)
        assertEquals(task.projectId, taskEntity?.projectId)
        assertEquals(task.tag, taskEntity?.tag)
    }

    @Test
    fun `TaskEntity to Task`() {
        val taskEntity = TaskEntity(1, "Name", "Description", TaskState.DOING, 2, Tag.GRAY)
        val task = taskEntity.toDomain()
        assertEquals(taskEntity.id, task?.id)
        assertEquals(taskEntity.name, task?.name)
        assertEquals(taskEntity.description, task?.description)
        assertEquals(taskEntity.state, task?.state)
        assertEquals(taskEntity.projectId, task?.projectId)
        assertEquals(taskEntity.tag, task?.tag)
    }
}
