package com.practicaltest.myapplication.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.practicaltest.myapplication.data.local.AppDatabase
import com.practicaltest.myapplication.data.local.UserDao
import com.practicaltest.myapplication.data.remote.UserRemoteDataSource
import com.practicaltest.myapplication.data.remote.UserService
import com.practicaltest.myapplication.data.repository.ApiSettings
import com.practicaltest.myapplication.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBaseUrl() = ApiSettings.BASE_API_URL

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(
        UserService::class.java
    )

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(userService: UserService) =
        UserRemoteDataSource(userService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.restaurantDao()


    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: UserRemoteDataSource,
        userDao: UserDao
    ) =
        UserRepository(remoteDataSource, userDao)

}