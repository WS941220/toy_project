package com.example.toy_project.ui.stray

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.Scoped.FragmentScoped
import com.example.toy_project.ui.memo.MemoContract
import com.example.toy_project.ui.memo.MemoFragment
import com.example.toy_project.ui.memo.MemoPresenter
import com.example.toy_project.ui.stray.strayt1.Strayt1Contract
import com.example.toy_project.ui.stray.strayt1.Strayt1Fragment
import com.example.toy_project.ui.stray.strayt1.Strayt1Presenter
import com.example.toy_project.ui.stray.strayt1.calendar.CalendarContract
import com.example.toy_project.ui.stray.strayt1.calendar.CalendarFragment
import com.example.toy_project.ui.stray.strayt1.calendar.CalendarPresenter
import com.example.toy_project.ui.stray.strayt1.location.LocationContract
import com.example.toy_project.ui.stray.strayt1.location.LocationFragment
import com.example.toy_project.ui.stray.strayt1.location.LocationPresenter
import com.example.toy_project.ui.stray.strayt2.Strayt2Contract
import com.example.toy_project.ui.stray.strayt2.Strayt2Fragment
import com.example.toy_project.ui.stray.strayt2.Strayt2Presenter
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
        fun strayPresenter(strayPresenter : StrayPresenter): StrayContract.StrayPresenter

        @ActivityScoped
        @Binds
        fun strayt1Presenter(strayt1Presenter : Strayt1Presenter): Strayt1Contract.Strayt1Presenter

        @ActivityScoped
        @Binds
        fun locationPresenter(locationPresenter : LocationPresenter): LocationContract.LocationPresenter

        @ActivityScoped
        @Binds
        fun calendarPresenter(calendarPresenter : CalendarPresenter): CalendarContract.CalendarPresenter

        @ActivityScoped
        @Binds
        fun strayt2Presenter(strayt2Presenter : Strayt2Presenter): Strayt2Contract.Strayt2Presenter

        @FragmentScoped
        @ContributesAndroidInjector
        fun strayt1Fragment(): Strayt1Fragment

        @FragmentScoped
        @ContributesAndroidInjector
        fun locationFragment(): LocationFragment

        @FragmentScoped
        @ContributesAndroidInjector
        fun calendarFragment(): CalendarFragment

        @FragmentScoped
        @ContributesAndroidInjector
        fun strayt2Fragment(): Strayt2Fragment
    }
}