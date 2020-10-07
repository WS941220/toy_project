package com.example.toy_project.ui.main.home

import com.example.toy_project.base.BaseContract
import com.example.toy_project.di.model.Category
import com.example.toy_project.util.CategoryAdapter

interface TalkContract {

    interface TalkView : BaseContract.View {
        fun showCategories(categoryItems: MutableList<Category>)
        fun errorDialog(message: String)
    }

    interface TalkPresenter : BaseContract.Presenter<TalkView> {
        fun getCategories()
    }
}