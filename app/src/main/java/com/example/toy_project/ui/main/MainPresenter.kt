package com.example.toy_project.ui.main

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.addeditmemo.AddEditMemoFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


@ActivityScoped
class MainPresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<MainContract.View?>(), MainContract.Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }


}
