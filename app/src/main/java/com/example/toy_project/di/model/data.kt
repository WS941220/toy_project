package com.example.toy_project.di.model

import com.google.gson.annotations.SerializedName


data class User(
    private var userid: String,
    private var userpwd: String
)

data class Token(
    @SerializedName("token") val token: String
)
