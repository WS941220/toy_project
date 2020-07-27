package com.example.toy_project.ui.stray.strayt1

import android.app.Activity
import android.content.Context
import com.example.toy_project.base.BaseContract
import com.example.toy_project.di.model.Item

interface Strayt1Contract {

    interface Strayt1View : BaseContract.View {
        fun showStrayList(strayList: List<Item>)
    }

    interface Strayt1Presenter : BaseContract.Presenter<Strayt1View> {
        fun getStrayList(stray: Map<String, String>)
    }
}