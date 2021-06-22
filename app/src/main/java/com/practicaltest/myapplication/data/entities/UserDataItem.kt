package com.practicaltest.myapplication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userDataItem")
class UserDataItem (
    @PrimaryKey
    var id: Int? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var avatar: String? = null
)