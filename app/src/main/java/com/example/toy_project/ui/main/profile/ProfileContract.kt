package com.example.toy_project.ui.main.home

import com.example.toy_project.base.BaseContract

interface ProfileContract {

    interface ProfileView : BaseContract.View {

    }

    interface ProfilePresenter : BaseContract.Presenter<ProfileView> {

    }
}