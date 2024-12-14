

package com.intern.aifortodo.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.intern.aifortodo.data.repository.UserPreferencesRepository.Companion.DATA_STORE_NAME
import com.intern.aifortodo.data.repository.UserPreferencesRepository.PreferencesKeys.APP_THEME
import com.intern.aifortodo.data.repository.UserPreferencesRepository.PreferencesKeys.PROJECT_SELECTED
import com.intern.aifortodo.domain.model.AppThemePreference
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class UserPreferencesRepository(private val context: Context) : IUserPreferencesRepository {

    override fun getProjectSelected(): Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[PROJECT_SELECTED] ?: 1
    }

    override suspend fun setProjectSelected(projectSelected: Int) {
        context.dataStore.edit { preferences ->
            preferences[PROJECT_SELECTED] = projectSelected
        }
    }

    override fun getAppThemePreference(): Flow<AppThemePreference> =
        context.dataStore.data.map { preferences ->
            preferences[APP_THEME]?.let { AppThemePreference.values().getOrNull(it) }
                ?: AppThemePreference.FOLLOW_SYSTEM
        }

    override suspend fun setAppThemePreference(theme: AppThemePreference) {
        context.dataStore.edit { preferences ->
            preferences[APP_THEME] = theme.ordinal
        }
    }

    override suspend fun clearPreferences() {
        context.dataStore.edit {
            it.clear()
        }
    }

    private object PreferencesKeys {
        val PROJECT_SELECTED = intPreferencesKey(PROJECT_SELECTED_KEY)
        val APP_THEME = intPreferencesKey(USER_THEME_KEY)
    }

    companion object {
        const val DATA_STORE_NAME = "preferences"
        private const val PROJECT_SELECTED_KEY = "project_selected"
        private const val USER_THEME_KEY = "user_theme"
    }
}
