package com.example.toy_project.ui.memo

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.Scoped.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [MemoModule.MemoAbstractModule::class])
class MemoModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface MemoAbstractModule {
        @ActivityScoped
        @Binds
        fun memoPresenter(presenter : MemoPresenter): MemoContract.Presenter

        @FragmentScoped
        @ContributesAndroidInjector
        fun memoFragment(): MemoFragment
    }
}