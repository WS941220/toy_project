package com.example.toy_project.ui.gallery

import com.example.toy_project.di.Scoped.ActivityScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module(includes = [GalleryModule.GalleryAbstractModule::class])
class GalleryModule {

    @ActivityScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Module
    interface GalleryAbstractModule {
        @ActivityScoped
        @Binds
        fun galleryPresenter(presenter : GalleryPresenter): GalleryContract.Presenter
    }
}