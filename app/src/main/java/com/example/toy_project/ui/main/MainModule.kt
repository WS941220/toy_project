package com.example.toy_project.ui.main

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.Scoped.FragmentScoped
import com.example.toy_project.ui.main.adopt.AdoptContract
import com.example.toy_project.ui.main.adopt.AdoptFragment
import com.example.toy_project.ui.main.adopt.AdoptPresenter
import com.example.toy_project.ui.main.chat.ChatContract
import com.example.toy_project.ui.main.chat.ChatPresenter
import com.example.toy_project.ui.main.home.*
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

        @ActivityScoped
        @Binds
        fun homePresenter(homepresenter : HomePresenter): HomeContract.HomePresenter

        @ActivityScoped
        @Binds
        fun adoptPresenter(adoptpresenter : AdoptPresenter): AdoptContract.AdoptPresenter

        @ActivityScoped
        @Binds
        fun talkPresenter(talkpresenter : TalkPresenter): TalkContract.TalkPresenter

        @ActivityScoped
        @Binds
        fun chatPresenter(chatpresenter : ChatPresenter): ChatContract.ChatPresenter

        @ActivityScoped
        @Binds
        fun profilePresenter(profilepresenter : ProfilePresenter): ProfileContract.ProfilePresenter

//        @FragmentScoped
//        @ContributesAndroidInjector
//        fun homeFragment(): HomeFragment
//
//        @FragmentScoped
//        @ContributesAndroidInjector
//        fun adoptFragment(): AdoptFragment
    }
}