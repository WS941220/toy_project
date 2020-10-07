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