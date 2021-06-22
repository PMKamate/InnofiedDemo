package com.practicaltest.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practicaltest.myapplication.data.entities.UserDataItem

@Dao
interface UserDao {

    @Query("SELECT * FROM userDataItem")
    fun getAllUserDataItem() : LiveData<List<UserDataItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userDataItem: List<UserDataItem>)

}