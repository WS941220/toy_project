package com.example.toy_project.di.module

import android.app.Application
import android.content.Context
import com.example.toy_project.di.Scoped.AppScoped
import com.example.toy_project.di.Scoped.PreferenceScoped
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Application?): Context

}