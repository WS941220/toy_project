package com.example.toy_project.ui.stray

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.Scoped.FragmentScoped
import com.example.toy_project.ui.memo.MemoContract
import com.example.toy_project.ui.memo.MemoFragment
import com.example.toy_project.ui.memo.MemoPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [StrayModule.StrayAbstractModule::class])
class StrayModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface StrayAbstractModule {
        @ActivityScoped
        @Binds
        fun strayPresenter(StrayPresenter : StrayPresenter): StrayContract.StrayPresenter

//        @FragmentScoped
//        @ContributesAndroidInjector
//        fun memoFragment(): MemoFragment
    }
}