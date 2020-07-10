package com.example.toy_project.ui.addeditmemo

import androidx.annotation.Nullable
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.Scoped.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [AddEditMemoModule.AddEditAbstractModule::class])
class AddEditMemoModule {

    @ActivityScoped
    @Provides
    fun provideMemoId(activity: AddEditMemoActivity): String? {
        return activity.intent.getStringExtra(AddEditMemoFragment.ARGUMENT_SHOW_MEMO_ID)
    }

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface AddEditAbstractModule {
        @ActivityScoped
        @Binds
        fun addEditMemoPresenter(presenter : AddEditMemoPresenter): AddEditMemoContract.Presenter

        @FragmentScoped
        @ContributesAndroidInjector
        fun addEditMemoFragment(): AddEditMemoFragment
    }
}