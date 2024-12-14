

package com.intern.aifortodo.data.localdatasource

import com.intern.aifortodo.data.database.dao.TaskDao
import com.intern.aifortodo.data.database.entity.TaskEntity
import com.intern.aifortodo.data.database.mapper.TaskMapper.toDomain
import com.intern.aifortodo.domain.model.Tag
import com.intern.aifortodo.domain.model.TaskState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskLocalDataSourceTest {

    @MockK
    private val taskDao = mockk<TaskDao>()

    private val taskLocalDataSource = TaskLocalDataSource(taskDao)

    @Test
    fun testGetTask() = runBlocking {
        val taskEntity = TaskEntity(1, "", "", TaskState.DOING, 1, Tag.GRAY)

        coEvery { taskDao.getTask(1) } returns flow {
            emit(taskEntity)
        }

        assertEquals(taskEntity.toDomain(), taskLocalDataSource.getTask(1).firstOrNull())
    }
}
