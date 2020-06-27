package com.example.toy_project.ui.main

import android.annotation.SuppressLint
import android.util.Log
import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.ApiService
import com.example.toy_project.di.Scoped.ActivityScoped
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScoped
class MainPresenter @Inject constructor(
    private val disposables: CompositeDisposable,
    private val apiService: ApiService
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

    @SuppressLint("CheckResult")
    override fun okBtn(id: String, pw: String) {
        val user = HashMap<String, String>()
        user["usrid"] = id
        user["usrpw"] = pw

        apiService.authenticate(user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribeBy(
            onNext = {
                Log.d("TEST",it.token)
            },

            onError = {
                Log.d("TEST2",it.message)
            }
        )
    }

}
