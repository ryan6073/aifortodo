

package com.intern.aifortodo.data.repository

import com.intern.aifortodo.data.localdatasource.ITaskLocalDataSource
import com.intern.aifortodo.domain.model.Tag
import com.intern.aifortodo.domain.model.Task
import com.intern.aifortodo.domain.model.TaskState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskRepositoryTest {

    @MockK
    private val taskLocalDataSource = mockk<ITaskLocalDataSource>()

    private val taskRepository = TaskRepository(taskLocalDataSource)

    @Test
    fun testGetTask() = runBlocking {
        val task = Task(1, "", "", TaskState.DOING, 1, Tag.GRAY)

        coEvery { taskLocalDataSource.getTask(1) } returns flow {
            emit(task)
        }

        assertEquals(task, taskRepository.getTask(1).firstOrNull())
    }
}
