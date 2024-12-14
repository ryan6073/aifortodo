

package com.intern.aifortodo.data.database.mapper

import com.intern.aifortodo.data.database.entity.ProjectEntity
import com.intern.aifortodo.data.database.entity.ProjectTasksRelation
import com.intern.aifortodo.data.database.mapper.TaskMapper.toDomain
import com.intern.aifortodo.domain.model.Project

object ProjectMapper {
    fun ProjectEntity?.toDomain(): Project? = this?.let {
        Project(
            id = it.id,
            name = it.name,
            description = it.description
        )
    }

    fun ProjectTasksRelation?.toDomain(): Project? = this?.let {
        Project(
            id = it.project.id,
            name = it.project.name,
            description = it.project.description,
            tasks = it.tasks.map { task -> task.toDomain() }.sortedBy { task -> task?.state }
        )
    }

    fun Project?.toEntity(): ProjectEntity? = this?.let {
        ProjectEntity(
            id = it.id,
            name = it.name,
            description = it.description
        )
    }
}
