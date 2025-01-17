package com.example.toy_project.ui.stray

import android.content.Context
import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.home.HomeContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class StrayPresenter @Inject constructor(
) : BasePresenter<StrayContract.StrayView?>(),
    StrayContract.StrayPresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
    }

    override fun attach(view: StrayContract.StrayView) {
        this.view = view
    }

}