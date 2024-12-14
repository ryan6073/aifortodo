

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Task
import com.intern.aifortodo.domain.repository.ITaskRepository
import kotlinx.coroutines.flow.Flow

class GetTaskUseCase(private val taskRepository: ITaskRepository) {

    /**
     * Gets a task by it id.
     *
     * @param id Task id.
     */
    operator fun invoke(id: Int): Flow<Task?> =
        taskRepository.getTask(id)
}
