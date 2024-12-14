

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.repository.ITaskRepository

class DeleteTaskUseCase(private val taskRepository: ITaskRepository) {

    /**
     * Deletes a task.
     */
    suspend operator fun invoke(taskId: Int) =
        taskRepository.deleteTask(taskId)
}
