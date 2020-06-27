package com.example.toy_project.base

abstract class BasePresenter<V> {
    protected var view: V? = null

    open fun subscribe(view: V) {
        this.view = view
    }

    open fun unsubscribe() {
        view = null
    }
}