package com.practicaltest.myapplication.data.repository

import android.util.Log
import com.practicaltest.myapplication.data.entities.UserDataItem
import com.practicaltest.myapplication.data.local.UserDao
import com.practicaltest.myapplication.data.remote.UserRemoteDataSource
import com.practicaltest.myapplication.entity.UserData
import com.practicaltest.myapplication.utils.UserListModel
import com.practicaltest.myapplication.utils.performGetOperation
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val userDao: UserDao) {

   /* fun getRestaurant(id: Int) = performDbGetOperation(
        databaseQuery = { restaurantDao.getRestaurantItem(id) }
    )

    fun getImageList(id: Int) = performDbGetOperation(
        databaseQuery = { imageDao.getAllImage(id) }
    )*/

    fun getRestaurant(page: String, per_page: String) = performGetOperation(
        databaseQuery = { userDao.getAllUserDataItem() },
        networkCall = { remoteDataSource.getAllUserList(page,per_page) },
        saveCallResult = {
            it.data.let { it1 ->
                val list = getRestaurantList(it1)
                userDao.insertAll(list.restaurantItemList)
            }
        }
    )

    fun getRestaurantList(restaurantListResponse: List<UserData>?): UserListModel {
        val restaurantItemList = ArrayList<UserDataItem>()
        restaurantListResponse?.let {
            for (item in it) {
                restaurantItemList.add(
                    UserDataItem(
                        item.id,
                        item.email,
                        item.firstName,
                        item.lastName,
                        item.avatar
                    )
                )

            }
        }
        Log.d("Test size: ", "" + restaurantItemList.size)
        return UserListModel(restaurantItemList)

    }

}