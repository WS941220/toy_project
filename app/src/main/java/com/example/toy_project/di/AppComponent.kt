package com.example.toy_project.di

import android.app.Application
import com.example.toy_project.base.BaseApp
import com.example.toy_project.di.Scoped.AppScoped
import com.example.toy_project.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(modules = [(ApplicationModule::class), (ActivityModule::class), (AndroidSupportInjectionModule::class), (NetworkModule::class), (LocalRepositoryModule::class)])
interface AppComponent : AndroidInjector<BaseApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}