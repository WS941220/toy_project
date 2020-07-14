package com.example.toy_project.ui.main.chat

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.MainContract
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class ChatPresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<ChatContract.ChatView?>(),
    ChatContract.ChatPresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: ChatContract.ChatView) {
        this.view = view
    }


}