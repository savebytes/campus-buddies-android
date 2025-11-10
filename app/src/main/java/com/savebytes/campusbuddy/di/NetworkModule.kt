package com.savebytes.campusbuddy.di

import com.savebytes.campusbuddy.BuildConfig
import com.savebytes.campusbuddy.data.remote.api.AuthApiService
import com.savebytes.campusbuddy.data.remote.interceptor.NetworkLoggerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkLoggerInterceptor: NetworkLoggerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                // Only add logging in debug builds
                if (BuildConfig.DEBUG) {
                    addInterceptor(networkLoggerInterceptor)
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // ✅ replace with your base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

}