

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.repository.IUserPreferencesRepository

class SetProjectSelectedUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository
) {

    /**
     * Sets the current project selected by its id.
     *
     * @param id Project id.
     */
    suspend operator fun invoke(id: Int) {
        userPreferencesRepository.setProjectSelected(id)
    }
}
