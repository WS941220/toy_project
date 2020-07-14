package com.example.toy_project.ui.login

import com.example.toy_project.di.Scoped.ActivityScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [LoginModule.LoginbstractModule::class])
class LoginModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface LoginbstractModule {
        @ActivityScoped
        @Binds
        fun loginPresenter(presenter : LoginPresenter): LoginContract.Presenter

//        @FragmentScoped
//        @ContributesAndroidInjector
//        fun mainFragment(): MainFragment
    }
}