

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.AppThemePreference
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository

class SetAppThemePreferenceUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository
) {

    /**
     * Updates the current selected [theme].
     */
    suspend operator fun invoke(theme: AppThemePreference) =
        userPreferencesRepository.setAppThemePreference(theme)
}
