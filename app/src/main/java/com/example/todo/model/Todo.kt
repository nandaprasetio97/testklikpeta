package com.example.todo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "date")
    var date: String,
) {
    constructor(date: String): this("", date)

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @ColumnInfo(name = "description")
    var description: String = ""
    @ColumnInfo(name = "latitude")
    var latitude: Double = 0.0
    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.0
}