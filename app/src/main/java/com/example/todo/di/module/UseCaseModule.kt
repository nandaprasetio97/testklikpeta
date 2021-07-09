package com.example.todo.di.module

import com.example.todo.domain.TodoService
import com.example.todo.domain.TodoServiceImpl
import com.example.todo.domain.cases.GetListUsesCase
import com.example.todo.domain.cases.GetTodoByIdUsesCase
import com.example.todo.domain.cases.SaveTodoUsesCase
import com.example.todo.domain.impl.GetListImpl
import com.example.todo.domain.impl.GetTodoByIdImpl
import com.example.todo.domain.impl.SaveTodoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @ViewModelScoped
    @Binds
    abstract fun bindGetListImpl(getListImpl: GetListImpl): GetListUsesCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetTodoByIdImpl(getTodoByIdImpl: GetTodoByIdImpl): GetTodoByIdUsesCase

    @ViewModelScoped
    @Binds
    abstract fun bindSaveTodoImpl(saveTodoImpl: SaveTodoImpl): SaveTodoUsesCase

    @ViewModelScoped
    @Binds
    abstract fun bindTodoServiceImpl(todoServiceImpl: TodoServiceImpl): TodoService
}