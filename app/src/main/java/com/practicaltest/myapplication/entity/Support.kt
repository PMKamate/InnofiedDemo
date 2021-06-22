package com.practicaltest.myapplication.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("url", "text")
class Support {
    @JsonProperty("url")
    var url: String? = null

    @JsonProperty("text")
    var text: String? = null
}