package com.example.todo.domain.impl

import com.example.todo.DateFormatException
import com.example.todo.LatLngException
import com.example.todo.RequestSealed
import com.example.todo.StringEmptyException
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.cases.SaveTodoUsesCase
import com.example.todo.model.Todo
import com.example.todo.utils.DateUtils
import javax.inject.Inject

class SaveTodoImpl @Inject constructor(
    private val dateUtils: DateUtils,
    private val todoLocalRepository: TodoRepository
): SaveTodoUsesCase {
    override suspend fun invoke(todo: Todo): RequestSealed {
        return try {
            RequestSealed.OnSuccess(
                when {
                    todo.title.isBlank() -> throw StringEmptyException()
                    todo.latitude == 0.0 && todo.longitude == 0.0 -> throw LatLngException()
                    dateUtils.checkFormatDate(todo.date) && todo.title.isNotBlank() -> 1L
                    !dateUtils.checkFormatDate(todo.date) -> throw DateFormatException()
                    else -> todoLocalRepository.saveTodo(todo)
                }
            )
        } catch (e: Throwable) {
            println(e)
            RequestSealed.OnFailure(e)
        }
    }
}