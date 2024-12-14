

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetProjectSelectedIdUseCase(
    userPreferencesRepository: IUserPreferencesRepository
) {

    /**
     * Retrieves the current project selected Id every time it changes in
     * user preferences.
     */
    val projectSelectedId: Flow<Int> = userPreferencesRepository.getProjectSelected()
}
