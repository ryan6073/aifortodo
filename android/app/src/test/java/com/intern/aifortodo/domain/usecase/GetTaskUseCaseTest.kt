

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Tag
import com.intern.aifortodo.domain.model.Task
import com.intern.aifortodo.domain.model.TaskState
import com.intern.aifortodo.domain.repository.ITaskRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTaskUseCaseTest {

    @MockK
    private val taskRepository = mockk<ITaskRepository>()

    private val getTaskUseCase = GetTaskUseCase(taskRepository)

    @Test
    fun testGetTaskUseCase() = runBlocking {
        val task = Task(1, "Name", "Description", TaskState.DOING, 1, Tag.GRAY)

        coEvery { taskRepository.getTask(1) } returns flow {
            emit(task)
        }

        assertEquals(task, getTaskUseCase(1).firstOrNull())
    }
}
