package com.example.toy_project.di

import android.app.Application
import com.example.toy_project.base.BaseApp
import com.example.toy_project.di.Scoped.AppScoped
import com.example.toy_project.di.module.ActivityModule
import com.example.toy_project.di.module.ApplicationModule
import com.example.toy_project.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(modules = [(NetworkModule::class), (ApplicationModule::class), (ActivityModule::class), (AndroidSupportInjectionModule::class)])
interface AppComponent : AndroidInjector<BaseApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}