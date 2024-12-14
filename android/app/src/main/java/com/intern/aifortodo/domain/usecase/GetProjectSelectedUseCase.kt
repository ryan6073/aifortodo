

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Project
import com.intern.aifortodo.domain.repository.IProjectRepository
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetProjectSelectedUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val projectRepository: IProjectRepository
) {

    /**
     * Retrieves the current project selected. This flow emits a value of a project
     * every time that current project selected in user preferences changes or
     * has been updated in database.
     *
     * @return A Flow that emits the current project selected.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Project?> =
        userPreferencesRepository.getProjectSelected().flatMapLatest { projectId ->
            projectRepository.getProject(projectId)
        }
}
