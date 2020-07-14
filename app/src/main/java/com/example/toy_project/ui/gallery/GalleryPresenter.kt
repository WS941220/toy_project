package com.example.toy_project.ui.gallery

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.addeditmemo.AddEditMemoFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class GalleryPresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<GalleryContract.View?>(), GalleryContract.Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: GalleryContract.View) {
        this.view = view
    }
}