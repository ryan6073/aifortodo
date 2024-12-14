

package com.intern.aifortodo.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.intern.aifortodo.data.database.view.TaskProjectView
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskProjectViewDao {

    @Query("SELECT * FROM TaskProjectView")
    fun getTaskProjectList(): Flow<List<TaskProjectView>>
}
