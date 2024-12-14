

package com.intern.aifortodo.data.localdatasource

import com.intern.aifortodo.data.database.dao.ProjectDao
import com.intern.aifortodo.data.database.entity.ProjectEntity
import com.intern.aifortodo.data.database.entity.ProjectTasksRelation
import com.intern.aifortodo.data.database.mapper.ProjectMapper.toDomain
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectLocalDataSourceTest {

    @MockK
    private val projectDao = mockk<ProjectDao>()

    private val projectLocalDataSource = ProjectLocalDataSource(projectDao)

    @Test
    fun testGetProjects() = runBlocking {
        val projects = listOf(ProjectEntity(1, "Name", "Description"))

        coEvery { projectDao.getProjects() } returns flow {
            emit(projects)
        }

        assertEquals(projects.map { it.toDomain() }, projectLocalDataSource.getProjects().firstOrNull())
    }

    @Test
    fun testGetProject() = runBlocking {
        val project = ProjectTasksRelation(ProjectEntity(1, "Name", "Description"), emptyList())

        coEvery { projectDao.getProject(1) } returns flow {
            emit(project)
        }

        assertEquals(project.toDomain(), projectLocalDataSource.getProject(1).firstOrNull())
    }
}
