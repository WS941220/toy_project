package com.example.toy_project.ui.stray.strayt1

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class Strayt1Presenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<Strayt1Contract.Strayt1View?>(),
    Strayt1Contract.Strayt1Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: Strayt1Contract.Strayt1View) {
        this.view = view
    }
}