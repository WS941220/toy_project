package com.example.toy_project.ui.stray

import android.content.Context
import com.example.toy_project.base.BaseContract

interface StrayContract {
    interface StrayView : BaseContract.View {

    }

    interface StrayPresenter : BaseContract.Presenter<StrayView> {

    }
}