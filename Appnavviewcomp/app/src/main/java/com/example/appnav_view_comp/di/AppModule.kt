package com.example.appnav_view_comp.di

import com.example.appnav_view_comp.data.repository.TaskInMemoryRepository
import com.example.appnav_view_comp.data.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(repository: TaskInMemoryRepository): TaskRepository
}