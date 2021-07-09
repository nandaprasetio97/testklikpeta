package com.example.todo.domain.impl

import com.example.todo.RequestSealed
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.cases.GetTodoByIdUsesCase
import com.example.todo.model.Todo
import com.example.todo.utils.DateUtils
import javax.inject.Inject

class GetTodoByIdImpl @Inject constructor(
    private val dateUtils: DateUtils,
    private val todoLocalRepository: TodoRepository
) : GetTodoByIdUsesCase {
    override suspend fun invoke(id: Int): RequestSealed {
        return try {
            RequestSealed.OnSuccess(
                when {
                    id > 0 -> todoLocalRepository.getTodoById(id)
                    else -> Todo(title = "", date = dateUtils.getCurrentDate()).also { it.id = null }
                }
            )
        } catch (e: Throwable) {
            RequestSealed.OnFailure(e)
        }
    }
}