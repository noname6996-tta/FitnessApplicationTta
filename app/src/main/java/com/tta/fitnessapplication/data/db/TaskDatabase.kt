package com.tta.fitnessapplication.data.db

import androidx.room.*
import com.tta.fitnessapplication.data.model.noti.CategoryInfo
import com.tta.fitnessapplication.data.model.noti.TaskInfo

@Database(entities = [TaskInfo::class, CategoryInfo::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskCategoryDao(): TaskCategoryDao
}