package com.example.toy_project.data

import com.example.toy_project.di.model.Stray
import io.reactivex.Flowable
import retrofit2.http.*

interface StrayService {

    @GET("abandonmentPublic")
    suspend fun getStrayList(
        @Query("bgnde") bgnde: String, @Query("endde") endde: String, @Query("upkind") upkind: String,
        @Query("upr_cd") upr_cd: String, @Query("org_cd") org_cd: String, @Query("care_reg_no") care_reg_no: String,
        @Query("pageNo") pageNo: String, @Query("numOfRows") numOfRows: String, @Query("serviceKey") serviceKey: String
    ): Stray

    @GET("sido")
    suspend fun getUprList(@Query("serviceKey") serviceKey: String): Stray

    @GET("sigungu")
    suspend fun getOrgList(@Query("upr_cd") upr_cd: String, @Query("serviceKey") serviceKey: String): Stray

    @GET("shelter")
    suspend fun getCareList(@Query("upr_cd") upr_cd: String, @Query("org_cd") org_cd: String, @Query("serviceKey") serviceKey: String): Stray
}