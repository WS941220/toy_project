package com.example.toy_project.ui.stray_detail

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class Stray_DetailPresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<Stray_DetailContract.View?>(),
    Stray_DetailContract.Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: Stray_DetailContract.View) {
        this.view = view
    }
}