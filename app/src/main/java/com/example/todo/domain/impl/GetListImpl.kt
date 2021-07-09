package com.example.todo.domain.impl

import com.example.todo.RequestSealed
import com.example.todo.data.todo.TodoRepository
import com.example.todo.domain.cases.GetListUsesCase
import com.example.todo.model.Todo
import com.example.todo.model.TodoCache
import com.example.todo.utils.DateUtils
import javax.inject.Inject

class GetListImpl @Inject constructor(
    private val dateUtils: DateUtils,
    private val todoRepository: TodoRepository
) : GetListUsesCase {
    override suspend fun invoke(): RequestSealed {
        return try {
            RequestSealed.OnSuccess(todoRepository.getListTodo().map {
                it.id?.let { id -> TodoCache(id = id, title = it.title, description = it.description, date = it.date) }
            }.let { list ->
                val mutableList: MutableList<TodoCache> = mutableListOf()
                val expiredMutableList: MutableList<TodoCache> = mutableListOf()
                val todayLong = dateUtils.getMilisByDate(dateUtils.getCurrentDate())
                list.forEach { todoCache ->
                    todoCache?.also {
                        val todoCacheDateLong = dateUtils.getMilisByDate(it.date)
                        if (todayLong > todoCacheDateLong) {
                            expiredMutableList.add(it)
                        } else {
                            mutableList.add(it)
                        }
                    }
                }
                mutableListOf<TodoCache>().also {
                    it.addAll(mutableList)
                    it.addAll(expiredMutableList)
                }
            })
        } catch (t: Throwable) {
            RequestSealed.OnFailure(t)
        }
    }
}