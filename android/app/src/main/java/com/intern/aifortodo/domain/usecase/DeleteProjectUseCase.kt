

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.repository.IProjectRepository
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteProjectUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val projectRepository: IProjectRepository
) {

    /**
     * Deletes a project. The deleted project will be the current project selected.
     * Once is deleted, it will select the first project in projects list if is not empty.
     */
    suspend operator fun invoke() {
        val projectId = userPreferencesRepository.getProjectSelected().firstOrNull()
        projectId?.let { projectRepository.deleteProject(it) }
        val projects = projectRepository.getProjects().firstOrNull()
        projects?.firstOrNull()?.let { project ->
            userPreferencesRepository.setProjectSelected(project.id)
        }
    }
}
