

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Project
import com.intern.aifortodo.domain.repository.IProjectRepository

class UpdateProjectUseCase(private val projectRepository: IProjectRepository) {

    /**
     * Updates a project.
     *
     * @param project Updated project that will replace current project.
     */
    suspend operator fun invoke(project: Project) =
        projectRepository.updateProject(project)
}
