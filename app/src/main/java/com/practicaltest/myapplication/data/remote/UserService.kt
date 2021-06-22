package com.practicaltest.myapplication.data.remote

import com.practicaltest.myapplication.entity.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("users")
    suspend fun getAllUserListResponse(
        @Query("page") page: String,
        @Query("per_page") per_page: String
    ): Response<UserDetails>
}