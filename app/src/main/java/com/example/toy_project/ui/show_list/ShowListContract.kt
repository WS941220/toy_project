package com.example.toy_project.ui.show_list

import com.example.toy_project.base.BaseContract

interface ShowListContract {
    interface View: BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getTalkList(): MutableList<String>
    }
}