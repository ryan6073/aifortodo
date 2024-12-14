

package com.intern.aifortodo.util

import com.intern.aifortodo.data.database.entity.ProjectEntity
import com.intern.aifortodo.data.database.entity.TaskEntity
import com.intern.aifortodo.domain.model.Tag
import com.intern.aifortodo.domain.model.TaskState

object TestUtil {

    fun createProject() = ProjectEntity(1, "Project", "Description")

    fun createTask() = TaskEntity(1, "Task", "Description", TaskState.DOING, 1, Tag.GRAY)
}
