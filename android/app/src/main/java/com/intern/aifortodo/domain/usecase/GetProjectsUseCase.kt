

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Project
import com.intern.aifortodo.domain.repository.IProjectRepository
import kotlinx.coroutines.flow.Flow

class GetProjectsUseCase(val projectRepository: IProjectRepository) {

    /**
     * Retrieves the list of projects every time it changes.
     */
    operator fun invoke(): Flow<List<Project?>> = projectRepository.getProjects()
}
