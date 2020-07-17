package com.example.toy_project.ui.stray.strayt1

import android.content.Context
import com.example.toy_project.base.BaseContract

interface Strayt1Contract {

    interface Strayt1View : BaseContract.View {

    }

    interface Strayt1Presenter : BaseContract.Presenter<Strayt1View> {
        fun callApi(baseContext: Context)
    }
}