package com.example.toy_project.ui.main.adopt

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class AdoptPresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<AdoptContract.AdoptView?>(),
    AdoptContract.AdoptPresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: AdoptContract.AdoptView) {
        this.view = view
    }


}