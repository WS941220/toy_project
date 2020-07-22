package com.example.toy_project.ui.stray.strayt1.location

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


@ActivityScoped
class LocationPresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<LocationContract.LocationView?>(),
    LocationContract.LocationPresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: LocationContract.LocationView) {
        this.view = view
    }


}