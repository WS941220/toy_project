package com.example.toy_project.ui.main

import android.content.Context
import com.example.toy_project.base.BaseContract


interface MainContract {
    interface View: BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {
        fun okBtn(id: String, pw: String, baseContext: Context)
        fun passBtn(baseContext: Context)
    }
}

