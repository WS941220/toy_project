package com.example.toy_project.base

import com.example.toy_project.di.DaggerAppComponent
import com.kakao.sdk.common.KakaoSdk
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class BaseApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        KakaoSdk.init(this, "28ceba19fa9796ed256b763889997ccb")
        return DaggerAppComponent.builder().application(this).build()
    }

}
