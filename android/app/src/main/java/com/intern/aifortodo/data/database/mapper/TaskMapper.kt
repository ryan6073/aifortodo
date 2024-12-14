

package com.intern.aifortodo.data.database.mapper

import com.intern.aifortodo.data.database.entity.TaskEntity
import com.intern.aifortodo.domain.model.Task

object TaskMapper {
    fun TaskEntity?.toDomain(): Task? = this?.let {
        Task(
            id = it.id,
            name = it.name,
            description = it.description,
            state = it.state,
            tag = it.tag,
            projectId = it.projectId
        )
    }

    fun Task?.toEntity(): TaskEntity? = this?.let {
        TaskEntity(
            id = it.id,
            name = it.name,
            description = it.description,
            state = it.state,
            tag = it.tag,
            projectId = it.projectId
        )
    }
}
