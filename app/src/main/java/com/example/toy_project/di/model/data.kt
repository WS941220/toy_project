package com.example.toy_project.di.model

import com.google.gson.annotations.SerializedName
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable


data class User(
    private var userid: String,
    private var userpwd: String
)

data class Token(
    @SerializedName("token") val token: String
)





