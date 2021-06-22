package com.practicaltest.myapplication.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "email", "first_name", "last_name", "avatar")
class UserData {
    @JsonProperty("id")
    var id: Int? = null

    @JsonProperty("email")
    var email: String? = null

    @JsonProperty("first_name")
    var firstName: String? = null

    @JsonProperty("last_name")
    var lastName: String? = null

    @JsonProperty("avatar")
    var avatar: String? = null
}