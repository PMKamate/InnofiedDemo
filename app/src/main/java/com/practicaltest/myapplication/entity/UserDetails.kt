package com.practicaltest.myapplication.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("page", "per_page", "total", "total_pages", "data", "support")
class UserDetails {
    @JsonProperty("page")
    var page: Int? = null

    @JsonProperty("per_page")
    var perPage: Int? = null

    @JsonProperty("total")
    var total: Int? = null

    @JsonProperty("total_pages")
    var totalPages: Int? = null

    @JsonProperty("data")
    var data: List<UserData>? = null

    @JsonProperty("support")
    var support: Support? = null
}
