package com.example.toy_project.ui.login

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.Scoped.AppScoped
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job

@Module(includes = [LoginModule.LoginAbstractModule::class])
class LoginModule {

    @ActivityScoped
    @Provides
    fun provideGoogleLogin(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("266363868586-52mk0acv23r57ceid0tl5q8nr5rk4ks8.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    @ActivityScoped
    @Provides
    fun provideJob(): Job {
        return Job()
    }

    @Module
    interface LoginAbstractModule {
        @ActivityScoped
        @Binds
        fun loginPresenter(presenter : LoginPresenter): LoginContract.Presenter
    }
}