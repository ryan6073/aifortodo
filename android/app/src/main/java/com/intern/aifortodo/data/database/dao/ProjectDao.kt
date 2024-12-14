

package com.intern.aifortodo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.intern.aifortodo.data.database.entity.ProjectEntity
import com.intern.aifortodo.data.database.entity.ProjectTasksRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProject(project: ProjectEntity): Long

    @Transaction
    @Query("SELECT * FROM project WHERE id = :id")
    fun getProject(id: Int): Flow<ProjectTasksRelation>

    @Query("SELECT * FROM project ORDER BY id ASC")
    fun getProjects(): Flow<List<ProjectEntity>>

    @Query("DELETE FROM project WHERE id = :id")
    suspend fun deleteProject(id: Int)

    @Update
    suspend fun updateProject(project: ProjectEntity)
}
