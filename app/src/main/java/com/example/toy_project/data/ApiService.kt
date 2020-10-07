package com.example.toy_project.data

import com.example.toy_project.di.model.Category
import com.example.toy_project.di.model.Token
import com.example.toy_project.di.model.User
import retrofit2.http.*

interface ApiService {

    @POST("/authenticate")
    @Headers("Content-Type: application/json")
    suspend fun authenticate(@Body user: User): Token

    @GET("/talkCategory/{clasnm}")
    @Headers("Content-Type: application/json")
    suspend fun getCategory(@Path("clasnm") clasnm: String): MutableList<Category>

}