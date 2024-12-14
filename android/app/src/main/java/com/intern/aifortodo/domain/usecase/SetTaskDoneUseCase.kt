

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.TaskState
import com.intern.aifortodo.domain.repository.ITaskRepository

class SetTaskDoneUseCase(private val taskRepository: ITaskRepository) {

    /**
     * Sets the taskState of a task to [TaskState.DONE].
     *
     * @param id Task id.
     */
    suspend operator fun invoke(id: Int) = taskRepository.setTaskDone(id)
}
