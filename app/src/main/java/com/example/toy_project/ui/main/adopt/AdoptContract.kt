package com.example.toy_project.ui.main.adopt

import com.example.toy_project.base.BaseContract
import com.example.toy_project.util.CategoryAdapter

interface AdoptContract {

    interface AdoptView : BaseContract.View {
        fun showCategories(categoryItems: MutableList<CategoryAdapter.Companion.Item>)
    }

    interface AdoptPresenter : BaseContract.Presenter<AdoptView> {
        fun getCategories()
    }
}