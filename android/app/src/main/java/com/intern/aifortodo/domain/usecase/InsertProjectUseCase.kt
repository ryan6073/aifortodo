

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Project
import com.intern.aifortodo.domain.repository.IProjectRepository
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository

class InsertProjectUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val projectRepository: IProjectRepository
) {

    /**
     * Creates a new project and set it as the project selected.
     *
     * @param name Project name.
     * @param description Project description.
     *
     * @return Id of new project, null if it could not be created.
     */
    suspend operator fun invoke(name: String, description: String): Long? =
        projectRepository.insert(Project(name = name, description = description))?.let {
            userPreferencesRepository.setProjectSelected(it.toInt())
            it
        }
}
