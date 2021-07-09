package com.example.todo.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.todo.ListTodoSealed
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 10,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object: Observer<T> {
        override fun onChanged(o: T?) {
            if (o is ListTodoSealed.Progress) {
                return
            }
            data = o
            latch.countDown()
            println("Countdown: $o")
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    println("Start observing")
    this.observeForever(observer)
    println("Finish observing")

    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}