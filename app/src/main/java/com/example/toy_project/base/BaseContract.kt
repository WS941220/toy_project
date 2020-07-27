package com.example.toy_project.base

import android.app.Activity

class BaseContract {

    interface Presenter<in T> {
        fun subscribe()
        fun unsubscribe()
        fun attach(view: T)
    }

    interface View {
        fun showProgress(msg: String)
        fun closeProgress()
    }
}