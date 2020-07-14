package com.example.toy_project.ui.main.home

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class ProfilePresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<ProfileContract.ProfileView?>(),
    ProfileContract.ProfilePresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: ProfileContract.ProfileView) {
        this.view = view
    }
}