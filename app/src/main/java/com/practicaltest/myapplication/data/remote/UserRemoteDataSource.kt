package com.practicaltest.myapplication.data.remote

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) : BaseDataSource() {
    suspend fun getAllUserList(page: String, per_page: String) =
        getResult { userService.getAllUserListResponse(page, per_page) }
}