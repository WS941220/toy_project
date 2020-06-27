package com.example.toy_project.di.model

import com.google.gson.annotations.SerializedName

data class User(
    val usrid: String,
   val usrpw: String
)

data class Token (
    @SerializedName("token") val token: String
)
