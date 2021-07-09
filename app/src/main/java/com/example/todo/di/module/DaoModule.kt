package com.example.todo.di.module

import com.example.todo.database.Database
import com.example.todo.database.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Singleton
    @Provides
    fun provideTodoDao(database: Database): TodoDao {
        return database.todoDao()
    }
}