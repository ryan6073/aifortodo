

package com.intern.aifortodo.data.database.mapper

import com.intern.aifortodo.data.database.view.TaskProjectView
import com.intern.aifortodo.domain.model.TaskProject

object TaskProjectMapper {

    fun TaskProjectView?.toDomain(): TaskProject? = this?.let {
        TaskProject(
            id = it.task.id,
            name = it.task.name,
            description = it.task.description,
            taskState = it.task.state,
            projectId = it.task.projectId,
            projectName = it.projectName,
            tag = it.task.tag
        )
    }
}
