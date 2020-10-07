package com.example.toy_project.ui.show_list

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.ApiService
import com.example.toy_project.di.Scoped.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class ShowListPresenter @Inject constructor(
    private val disposables: CompositeDisposable,
    private val apiService: ApiService
) : BasePresenter<ShowListContract.View?>(), ShowListContract.Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: ShowListContract.View) {
        this.view = view
    }

    override fun getTalkList(): MutableList<String> {
        return arrayListOf("aa")
    }
}