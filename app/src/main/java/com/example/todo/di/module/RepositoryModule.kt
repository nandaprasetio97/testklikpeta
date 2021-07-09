package com.example.todo.di.module

import com.example.todo.data.todo.TodoLocalRepositoryImpl
import com.example.todo.data.todo.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindTodoRepository(todoLocalRepositoryImpl: TodoLocalRepositoryImpl): TodoRepository
}