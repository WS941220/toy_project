package com.example.toy_project.base

import com.example.toy_project.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class BaseApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}
