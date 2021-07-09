package com.example.todo.di.module

import com.example.todo.utils.DateUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {
    @Singleton
    @Provides
    fun provideDateUtils(): DateUtils {
        return DateUtils()
    }
}