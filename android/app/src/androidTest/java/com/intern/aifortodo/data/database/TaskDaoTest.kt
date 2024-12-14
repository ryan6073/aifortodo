

package com.intern.aifortodo.data.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.intern.aifortodo.data.database.dao.ProjectDao
import com.intern.aifortodo.data.database.dao.TaskDao
import com.intern.aifortodo.domain.model.TaskState
import com.intern.aifortodo.util.TestUtil
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest : TodometerDatabaseTest() {
    private lateinit var taskDao: TaskDao

    private lateinit var projectDao: ProjectDao

    @Before
    fun init() {
        taskDao = todometerDatabase.taskDao()
        projectDao = todometerDatabase.projectDao()
        runBlocking {
            projectDao.insertProject(
                TestUtil.createProject()
            )
        }
    }

    @Test
    fun testInsertTask() = runBlocking {
        val taskA = TestUtil.createTask()
        val id = taskDao.insertTask(taskA).toInt()
        val taskB = taskDao.getTask(id).firstOrNull()
        assertThat(taskB, `is`(taskA))
    }

    @Test
    fun testGetTask() = runBlocking {
        val id = taskDao.insertTask(
            TestUtil.createTask()
        ).toInt()
        val task = taskDao.getTask(id).firstOrNull()
        assertNotNull(task)
    }

    @Test
    fun testGetTaskNotExist() = runBlocking {
        val task = taskDao.getTask(10).firstOrNull()
        assertNull(task)
    }

    @Test
    fun testGetTasks() = runBlocking {
        taskDao.insertTask(
            TestUtil.createTask()
        )
        val tasks = taskDao.getTasks().firstOrNull()
        assertFalse(tasks.isNullOrEmpty())
    }

    @Test
    fun testUpdateTask() = runBlocking {
        val id = taskDao.insertTask(
            TestUtil.createTask()
        ).toInt()
        var task = taskDao.getTask(id).firstOrNull()
        assertEquals("Task", task?.name)

        task?.name = "New name"
        task?.let { taskDao.updateTask(it) }

        task = taskDao.getTask(id).firstOrNull()
        assertEquals("New name", task?.name)
    }

    @Test
    fun testSetTaskStateDone() = runBlocking {
        val id = taskDao.insertTask(
            TestUtil.createTask()
        ).toInt()
        assertThat(taskDao.getTask(id).firstOrNull()?.state, `is`(TaskState.DOING))
        taskDao.setTaskDone(id)
        assertThat(taskDao.getTask(id).firstOrNull()?.state, `is`(TaskState.DONE))
    }

    @Test
    fun testSetTaskStateDoing() = runBlocking {
        val id = taskDao.insertTask(
            TestUtil.createTask()
        ).toInt()
        assertThat(taskDao.getTask(id).firstOrNull()?.state, `is`(TaskState.DOING))
        taskDao.setTaskDone(id)
        assertThat(taskDao.getTask(id).firstOrNull()?.state, `is`(TaskState.DONE))
        taskDao.setTaskDoing(id)
        assertThat(taskDao.getTask(id).firstOrNull()?.state, `is`(TaskState.DOING))
    }

    @Test
    fun testDeleteTask() = runBlocking {
        val id = taskDao.insertTask(
            TestUtil.createTask()
        ).toInt()
        assertNotNull(taskDao.getTask(id).firstOrNull())
        taskDao.deleteTask(id)
        assertNull(taskDao.getTask(id).firstOrNull())
    }
}
