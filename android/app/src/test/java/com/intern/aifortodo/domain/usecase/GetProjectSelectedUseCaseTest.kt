

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Project
import com.intern.aifortodo.domain.repository.IProjectRepository
import com.intern.aifortodo.domain.repository.IUserPreferencesRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProjectSelectedUseCaseTest {

    @MockK
    private val userPreferencesRepository = mockk<IUserPreferencesRepository>()

    @MockK
    private val projectRepository = mockk<IProjectRepository>()

    private val getProjectSelectedUseCase =
        GetProjectSelectedUseCase(userPreferencesRepository, projectRepository)

    @Test
    fun testGetProjectSelectedUseCase() = runBlocking {
        val project = Project(1, "Name", "Description")

        coEvery { userPreferencesRepository.getProjectSelected() } returns flow {
            emit(1)
        }
        coEvery { projectRepository.getProject(1) } returns flow {
            emit(project)
        }

        assertEquals(project, getProjectSelectedUseCase().firstOrNull())
    }
}
