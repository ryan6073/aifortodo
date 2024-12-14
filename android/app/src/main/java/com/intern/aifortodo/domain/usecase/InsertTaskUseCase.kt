

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Tag
import com.intern.aifortodo.domain.model.Task
import com.intern.aifortodo.domain.model.TaskState
import com.intern.aifortodo.domain.repository.ITaskRepository
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull

class InsertTaskUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val taskRepository: ITaskRepository
) {

    /**
     * Creates a new task in the current project selected.
     *
     * @param name Task name.
     * @param description Task description.
     * @param tag Task tag.
     * @param taskState Task state.
     * @return Id of new task, null if the current project selected is null or could not be created.
     */
    suspend operator fun invoke(
        name: String,
        description: String,
        tag: Tag,
        taskState: TaskState
    ): Long? =
        userPreferencesRepository.getProjectSelected().firstOrNull()?.let {
            taskRepository.insert(
                Task(
                    name = name,
                    description = description,
                    projectId = it,
                    tag = tag,
                    state = taskState
                )
            )
        }
}
