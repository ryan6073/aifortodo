

package com.intern.aifortodo.data.repository

import com.intern.aifortodo.data.localdatasource.IProjectLocalDataSource
import com.intern.aifortodo.domain.model.Project
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectRepositoryTest {

    @MockK
    private val projectLocalDataSource = mockk<IProjectLocalDataSource>()

    private val projectRepository = ProjectRepository(projectLocalDataSource)

    @Test
    fun testGetProjects() = runBlocking {
        val projects = listOf(Project(1, "Name", "Description"))

        coEvery { projectLocalDataSource.getProjects() } returns flow {
            emit(projects)
        }

        assertEquals(projects, projectRepository.getProjects().firstOrNull())
    }

    @Test
    fun testGetProject() = runBlocking {
        val project = Project(1, "Name", "Description")

        coEvery { projectLocalDataSource.getProject(1) } returns flow {
            emit(project)
        }

        assertEquals(project, projectRepository.getProject(1).firstOrNull())
    }
}
