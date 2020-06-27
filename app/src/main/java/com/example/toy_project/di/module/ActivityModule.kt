package com.example.toy_project.di.module

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.MainActivity
import com.example.toy_project.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

}