/*
 * Copyright 2022 Sergio Belda Galbis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intern.aifortodo.data.localdatasource

import com.intern.aifortodo.data.database.dao.TaskDao
import com.intern.aifortodo.data.database.mapper.TaskMapper.toDomain
import com.intern.aifortodo.data.database.mapper.TaskMapper.toEntity
import com.intern.aifortodo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskLocalDataSource(private val taskDao: TaskDao) : ITaskLocalDataSource {

    override fun getTask(id: Int): Flow<Task?> = taskDao.getTask(id).map { it.toDomain() }

    override suspend fun deleteTask(id: Int) {
        taskDao.deleteTask(id)
    }

    override suspend fun insert(task: Task): Long? =
        task.toEntity()?.let { taskDao.insertTask(it) }

    override suspend fun setTaskDone(id: Int) {
        taskDao.setTaskDone(id)
    }

    override suspend fun setTaskDoing(id: Int) {
        taskDao.setTaskDoing(id)
    }

    override suspend fun updateTask(task: Task) {
        task.toEntity()?.let { taskDao.updateTask(it) }
    }
}
