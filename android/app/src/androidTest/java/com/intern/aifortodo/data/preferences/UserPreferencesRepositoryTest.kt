

package com.intern.aifortodo.data.preferences

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.intern.aifortodo.data.repository.UserPreferencesRepository
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserPreferencesRepositoryTest {

    lateinit var userPreferencesRepository: IUserPreferencesRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        userPreferencesRepository = UserPreferencesRepository(context)
    }

    @Test
    fun testGetProjectSelectedDefault() = runBlocking {
        assertEquals(1, userPreferencesRepository.getProjectSelected().firstOrNull())
    }

    @Test
    fun testSetProjectSelected() = runBlocking {
        userPreferencesRepository.setProjectSelected(2)
        assertEquals(2, userPreferencesRepository.getProjectSelected().firstOrNull())
    }

    @After
    fun clearPreferences() = runBlocking {
        userPreferencesRepository.clearPreferences()
    }
}
