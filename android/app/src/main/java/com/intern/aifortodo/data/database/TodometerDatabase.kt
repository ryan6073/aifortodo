package com.intern.aifortodo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.intern.aifortodo.data.database.dao.ProjectDao
import com.intern.aifortodo.data.database.dao.TaskDao
import com.intern.aifortodo.data.database.dao.TaskProjectViewDao
import com.intern.aifortodo.data.database.entity.ProjectEntity
import com.intern.aifortodo.data.database.entity.TaskEntity
import com.intern.aifortodo.data.database.view.TaskProjectView

@Database(
    entities = [ProjectEntity::class, TaskEntity::class],
    views = [TaskProjectView::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TodometerDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    abstract fun taskDao(): TaskDao

    abstract fun projectTaskViewDao(): TaskProjectViewDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("UPDATE Task SET tag = 'PINK' WHERE tag = 'DB'")
        database.execSQL("UPDATE Task SET tag = 'RED' WHERE tag = 'UI'")
        database.execSQL("UPDATE Task SET tag = 'INDIGO' WHERE tag = 'AR_VR'")
        database.execSQL("UPDATE Task SET tag = 'BLUE' WHERE tag = 'ARCH'")
        database.execSQL("UPDATE Task SET tag = 'BLUE' WHERE tag = 'WEB'")
        database.execSQL("UPDATE Task SET tag = 'TEAL' WHERE tag = 'CAM'")
        database.execSQL("UPDATE Task SET tag = 'GREEN' WHERE tag = 'MAPS'")
        database.execSQL("UPDATE Task SET tag = 'LIME' WHERE tag = 'ML'")
        database.execSQL("UPDATE Task SET tag = 'YELLOW' WHERE tag = 'IOT'")
        database.execSQL("UPDATE Task SET tag = 'AMBER' WHERE tag = 'JETPACK'")
        database.execSQL("UPDATE Task SET tag = 'ORANGE' WHERE tag = 'MEDIA'")
        database.execSQL("UPDATE Task SET tag = 'ORANGE' WHERE tag = 'KOTLIN_NATIVE'")
        database.execSQL("UPDATE Task SET tag = 'ORANGE' WHERE tag = 'KOTLIN_JS'")
        database.execSQL("UPDATE Task SET tag = 'GRAY' WHERE tag = 'OTHER'")
    }
}
