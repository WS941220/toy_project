package com.example.toy_project.ui.main.home

import com.example.toy_project.base.BaseContract

interface HomeContract {

    interface HomeView : BaseContract.View {

    }

    interface HomePresenter : BaseContract.Presenter<HomeView> {

    }
}