package com.savebytes.campusbuddy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide App Context
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) : Context = context

}