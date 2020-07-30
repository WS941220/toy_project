package com.example.toy_project.ui.stray_detail

import com.example.toy_project.di.Scoped.ActivityScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [Stray_DetailModule.Stray_DetailAbstractModule::class])
class Stray_DetailModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface Stray_DetailAbstractModule {
        @ActivityScoped
        @Binds
        fun stray_detailPresenter(presenter : Stray_DetailPresenter): Stray_DetailContract.Presenter
    }
}