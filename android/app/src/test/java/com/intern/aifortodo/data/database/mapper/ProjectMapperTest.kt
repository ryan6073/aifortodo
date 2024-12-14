

package com.intern.aifortodo.data.database.mapper

import com.intern.aifortodo.data.database.entity.ProjectEntity
import com.intern.aifortodo.data.database.entity.ProjectTasksRelation
import com.intern.aifortodo.data.database.mapper.ProjectMapper.toDomain
import com.intern.aifortodo.data.database.mapper.ProjectMapper.toEntity
import com.intern.aifortodo.domain.model.Project
import org.junit.Assert.assertEquals
import org.junit.Test

class ProjectMapperTest {

    @Test
    fun `Project to ProjectEntity`() {
        val project = Project(1, "Name", "Description")
        val projectEntity = project.toEntity()
        assertEquals(project.id, projectEntity?.id)
        assertEquals(project.name, projectEntity?.name)
        assertEquals(project.description, projectEntity?.description)
    }

    @Test
    fun `ProjectEntity to Project`() {
        val projectEntity = ProjectEntity(1, "Name", "Description")
        val project = projectEntity.toDomain()
        assertEquals(projectEntity.id, project?.id)
        assertEquals(projectEntity.name, project?.name)
        assertEquals(projectEntity.description, project?.description)
    }

    @Test
    fun `ProjectTaskRelation to Project`() {
        val projectEntity = ProjectEntity(1, "Name", "Description")
        val projectTasksRelation = ProjectTasksRelation(projectEntity, emptyList())
        val project = projectTasksRelation.toDomain()
        assertEquals(projectTasksRelation.project.id, project?.id)
        assertEquals(projectTasksRelation.project.name, project?.name)
        assertEquals(projectTasksRelation.project.description, project?.description)
    }
}
