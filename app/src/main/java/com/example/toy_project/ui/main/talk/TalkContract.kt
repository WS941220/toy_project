package com.example.toy_project.ui.main.home

import com.example.toy_project.base.BaseContract

interface TalkContract {

    interface TalkView : BaseContract.View {

    }

    interface TalkPresenter : BaseContract.Presenter<TalkView> {

    }
}