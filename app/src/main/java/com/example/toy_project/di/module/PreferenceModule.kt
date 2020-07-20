package com.example.toy_project.di.module

import android.app.Application
import com.example.toy_project.base.BaseApp
import com.example.toy_project.di.Scoped.AppScoped
import com.example.toy_project.di.Scoped.PreferenceScoped
import com.example.toy_project.util.SettingPreference
import dagger.Module
import dagger.Provides

@Module
class PreferenceModule {

    @AppScoped
    @Provides
    fun provideSettingPreference(context: Application) = SettingPreference(context)

    @PreferenceScoped
    @Provides
    fun providePrefFileName(): String = "toy_pref"
}