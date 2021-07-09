package com.example.todo.presentation.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.CreateTodoSealed
import com.example.todo.ListTodoSealed
import com.example.todo.RequestSealed
import com.example.todo.domain.TodoService
import com.example.todo.model.Todo
import com.example.todo.model.TodoCache
import com.example.todo.utils.DateUtils
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoService: TodoService,
    private val dateUtils: DateUtils
) : ViewModel() {
    private val mTodoCacheLiveData: MutableLiveData<ListTodoSealed> = MutableLiveData()
    val observerList get() = mTodoCacheLiveData

    private val mTodoState: MutableLiveData<CreateTodoSealed> = MutableLiveData()
    val observerSave get() = mTodoState

    private val mTitleLiveData: MutableLiveData<String> = MutableLiveData()
    val observerTitle get() = mTitleLiveData

    private val mDescriptionLiveData: MutableLiveData<String> = MutableLiveData()
    val observerDescription get() = mDescriptionLiveData

    private val mDateLiveData: MutableLiveData<String> = MutableLiveData()
    val observerDate get() = mDateLiveData

    private val mLatLngLiveData: MutableLiveData<LatLng> = MutableLiveData()
    val observerLatLng get() = mLatLngLiveData

    fun getTodoList() {
        mTodoCacheLiveData.value = ListTodoSealed.Progress
        viewModelScope.launch(Dispatchers.IO) {
            mTodoCacheLiveData.postValue(
                todoService.getTodoList().let {
                    @Suppress("UNCHECKED_CAST")
                    when (it) {
                        is RequestSealed.OnSuccess<*> -> ListTodoSealed.OnSuccess(it.data as List<TodoCache>)
                        is RequestSealed.OnFailure -> ListTodoSealed.OnFailure(it.err)
                    }
                }
            )
        }
    }

    fun getTodoById(id: Int) {
        mTodoState.value = CreateTodoSealed.OnProgressGet
        viewModelScope.launch(Dispatchers.IO) {
            mTodoState.postValue(
                todoService.getTodoById(id).let {
                    @Suppress("UNCHECKED_CAST")
                    when (it) {
                        is RequestSealed.OnSuccess<*> -> CreateTodoSealed.OnGetSuccess(it.data as Todo)
                        is RequestSealed.OnFailure -> CreateTodoSealed.OnFailure(it.err)
                    }
                }
            )
        }
    }

    private fun saveTodo(todo: Todo) {
        mTodoState.value = CreateTodoSealed.OnProgressGet
        viewModelScope.launch(Dispatchers.IO) {
            mTodoState.postValue(
                todoService.getTodoList().let {
                    @Suppress("UNCHECKED_CAST")
                    when (it) {
                        is RequestSealed.OnSuccess<*> -> CreateTodoSealed.OnSaveSuccess
                        is RequestSealed.OnFailure -> CreateTodoSealed.OnFailure(it.err)
                    }
                }
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getCalender(view: View) {
        DatePickerDialog(view.context).show()
    }

    /*private fun getHours(context: Context, calendar: Calendar) {
        TimePickerDialog(
            context, TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->  },
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        ).show()
    }*/

    fun submit(todo: Todo) {
        saveTodo(todo)
    }
}