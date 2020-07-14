package com.example.toy_project.ui.main.adopt

import com.example.toy_project.base.BaseContract

interface AdoptContract {

    interface AdoptView : BaseContract.View {

    }

    interface AdoptPresenter : BaseContract.Presenter<AdoptView> {

    }
}