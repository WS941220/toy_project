package com.example.toy_project.ui.show_list

import com.example.toy_project.di.Scoped.ActivityScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [ShowListModule.ShowListAbstractModule::class])
class ShowListModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface ShowListAbstractModule {
        @ActivityScoped
        @Binds
        fun showListPresenter(presenter : ShowListPresenter): ShowListContract.Presenter

    }
}