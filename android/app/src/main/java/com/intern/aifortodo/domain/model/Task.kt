

package com.intern.aifortodo.domain.model

data class Task(
    val id: Int = 0,
    val name: String,
    val description: String?,
    val state: TaskState,
    val projectId: Int,
    val tag: Tag?
)
