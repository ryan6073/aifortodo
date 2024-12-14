

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Task
import com.intern.aifortodo.domain.repository.ITaskRepository

class UpdateTaskUseCase(private val taskRepository: ITaskRepository) {

    /**
     * Updates a task.
     *
     * @param task Updated task that will replace current task.
     */
    suspend operator fun invoke(task: Task) = taskRepository.updateTask(task)
}
