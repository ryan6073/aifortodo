

package com.intern.aifortodo.domain.usecase

import com.intern.aifortodo.domain.model.Project
import com.intern.aifortodo.domain.repository.IProjectRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProjectsUseCaseTest {

    @MockK
    private val projectRepository = mockk<IProjectRepository>()

    private val getProjectsUseCase = GetProjectsUseCase(projectRepository)

    @Test
    fun testGetProjectsUseCase() = runBlocking {
        val projects = listOf(Project(1, "Name", "Description"))

        coEvery { projectRepository.getProjects() } returns flow {
            emit(projects)
        }

        assertEquals(projects, getProjectsUseCase().firstOrNull())
    }
}
