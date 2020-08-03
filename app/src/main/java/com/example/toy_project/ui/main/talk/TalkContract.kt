package com.example.toy_project.ui.main.home

import com.example.toy_project.base.BaseContract
import com.example.toy_project.util.CategoryAdapter

interface TalkContract {

    interface TalkView : BaseContract.View {
        fun showCategories(categoryItems: MutableList<CategoryAdapter.Companion.Item>)
    }

    interface TalkPresenter : BaseContract.Presenter<TalkView> {
        fun getCategories()
    }
}