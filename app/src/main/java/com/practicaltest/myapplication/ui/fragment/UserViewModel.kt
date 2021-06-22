package com.practicaltest.myapplication.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.practicaltest.myapplication.data.entities.UserDataItem
import com.practicaltest.myapplication.data.repository.UserRepository
import com.practicaltest.myapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    fun getUserDetails(page: String, per_page: String): LiveData<Resource<List<UserDataItem>>> {
        return repository.getRestaurant(page, per_page)
    }
}
