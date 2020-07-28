package com.example.toy_project.data

import com.example.toy_project.di.model.Stray
import io.reactivex.Flowable
import retrofit2.http.*

interface StrayService {

    @GET("abandonmentPublic")
    fun getStrayList(
        @Query("bgnde") bgnde: String, @Query("endde") endde: String, @Query("upkind") upkind: String,
        @Query("upr_cd") upr_cd: String, @Query("org_cd") org_cd: String, @Query("care_reg_no") care_reg_no: String,
        @Query("pageNo") pageNo: String, @Query("numOfRows") numOfRows: String, @Query("serviceKey") serviceKey: String
    ): Flowable<Stray>

    @GET("sido")
    fun getUprList(@Query("serviceKey") serviceKey: String): Flowable<Stray>

    @GET("sigungu")
    fun getOrgList(@Query("upr_cd") upr_cd: String, @Query("serviceKey") serviceKey: String): Flowable<Stray>

    @GET("shelter")
    fun getCareList(@Query("upr_cd") upr_cd: String, @Query("org_cd") org_cd: String, @Query("serviceKey") serviceKey: String): Flowable<Stray>
}