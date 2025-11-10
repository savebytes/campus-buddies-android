package com.savebytes.campusbuddy.di

import android.content.Context
import com.savebytes.campusbuddy.data.local.datastore.DataPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    // Provide DataStore
    @Provides
    @Singleton
    fun provideDataPrefManager(context: Context): DataPrefManager {
        return DataPrefManager(context)
    }

}