package com.example.toy_project.data

import com.example.toy_project.di.Scoped.AppScoped
import com.example.toy_project.di.model.Token
import com.example.toy_project.di.model.User
import dagger.Provides
import io.reactivex.Flowable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("/api/authenticate")
    @Headers("Content-Type: application/json")
    fun authenticate(@Body user: HashMap<String, String>): Flowable<Token>

}