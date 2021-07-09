package com.example.todo.di.module

import android.content.Context
import androidx.room.Room
import com.example.todo.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext applicationContext: Context): Database {
        return Room.databaseBuilder(applicationContext, Database::class.java, "movie_catalog")
            .build()
    }
}