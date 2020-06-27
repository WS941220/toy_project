package com.example.toy_project.ui.main

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.Scoped.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [MainModule.MainAbstractModule::class])
class MainModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface MainAbstractModule {
        @ActivityScoped
        @Binds
        fun mainPresenter(presenter : MainPresenter): MainContract.Presenter

//        @FragmentScoped
//        @ContributesAndroidInjector
//        fun mainFragment(): MainFragment
    }
}