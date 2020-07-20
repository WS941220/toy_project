package com.example.toy_project.di.module

import com.example.toy_project.data.ApiService
import com.example.toy_project.data.StrayService
import com.example.toy_project.di.Scoped.AppScoped
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named


@Module
class NetworkModule {

    @AppScoped
    @Provides
    fun provideApiService(@Named("provideRetrofit") retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @AppScoped
    @Provides
    @Named("provideRetrofit")
    fun provideRetrofitInterface() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.226:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient())
            .build()
    }

    @AppScoped
    @Provides
    fun provideStrayService(@Named("provideRetrofit2") retrofit: Retrofit) : StrayService {
        return retrofit.create(StrayService::class.java)
    }

    @AppScoped
    @Provides
    @Named("provideRetrofit2")
    fun provideRetrofitInterface2() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient())
            .build()
    }


    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }
}