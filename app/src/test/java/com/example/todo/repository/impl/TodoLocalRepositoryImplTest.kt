package com.example.todo.repository.impl

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todo.database.Database
import com.example.todo.database.dao.TodoDao
import com.example.todo.model.Todo
import com.example.todo.utils.DateUtils
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

class TodoLocalRepositoryImplTest {
    private lateinit var todoDao: TodoDao
    private lateinit var database: Database
    private lateinit var dateUtils: DateUtils

    @BeforeEach
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, Database::class.java).build()
        todoDao = database.todoDao()
    }

    @Test
    fun saveTodo() {
        val todo = Todo("Test", "2014-10-12")
        todoDao.saveTodo(todo)
        val receivedTodo = todoDao.getTodoById(1)
        assert(todo == receivedTodo)
    }

    @Test
    fun getTodoById() {

    }

    @Test
    fun getListTodo() {

    }

    @AfterEach
    fun after() {
        database.close()
    }
}