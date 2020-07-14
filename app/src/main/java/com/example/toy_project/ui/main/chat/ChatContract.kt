package com.example.toy_project.ui.main.chat

import com.example.toy_project.base.BaseContract

interface ChatContract {

    interface ChatView : BaseContract.View {

    }

    interface ChatPresenter : BaseContract.Presenter<ChatView> {

    }
}