

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.AppThemePreference
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAppThemePreferenceUseCase @Inject constructor(
    userPreferencesRepository: IUserPreferencesRepository
) {

    /**
     * Retrieves the current selected theme in user preferences
     * every time it changes.
     */
    val appThemePreference: Flow<AppThemePreference> =
        userPreferencesRepository.getAppThemePreference()
}
