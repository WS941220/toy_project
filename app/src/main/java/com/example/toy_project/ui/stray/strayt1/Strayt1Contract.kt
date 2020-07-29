package com.example.toy_project.ui.stray.strayt1

import android.app.Activity
import android.content.Context
import com.example.toy_project.base.BaseContract
import com.example.toy_project.di.model.Item

interface Strayt1Contract {

    interface Strayt1View : BaseContract.View {
        fun showStrayList(strayList: List<Item>)
        fun setBottomTitle(s_date: String, e_date: String)
    }

    interface Strayt1Presenter : BaseContract.Presenter<Strayt1View> {
        fun getStrayList(num: Int)
        fun setDefault(s_date: String, e_date: String, upr_cd: String, org_cd: String, care_reg_no: String)
    }
}