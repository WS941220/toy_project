package com.example.toy_project.ui.stray.strayt2

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class Strayt2Presenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<Strayt2Contract.Strayt2View?>(),
    Strayt2Contract.Strayt2Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: Strayt2Contract.Strayt2View) {
        this.view = view
    }
}