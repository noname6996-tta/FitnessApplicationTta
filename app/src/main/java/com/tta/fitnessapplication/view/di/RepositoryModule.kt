package com.example.todolist.presentation.di

import com.tta.fitnessapplication.data.db.TaskCategoryDao
import com.tta.fitnessapplication.data.repository.TaskCategoryRepositoryImpl
import com.tta.fitnessapplication.domain.TaskCategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskCategoryRepository(taskCategoryDao: TaskCategoryDao) : TaskCategoryRepository {
        return TaskCategoryRepositoryImpl(taskCategoryDao)
    }
}