package com.example.toy_project.ui.main.adopt

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.CategoryAdapter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class AdoptPresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<AdoptContract.AdoptView?>(),
    AdoptContract.AdoptPresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: AdoptContract.AdoptView) {
        this.view = view
    }

    override fun getCategories() {
        val categoryItems: MutableList<CategoryAdapter.Companion.Item> = arrayListOf()
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "분양", true))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "파충류 분양", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "양서류 분양", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "절지류 분양", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "기타동물 분양", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "입양/구매/교환", true))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "파충류", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "양서류", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "절지류", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "기타동물", false))
        val places: CategoryAdapter.Companion.Item =
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "제휴/업체",true).apply {
                invisibleChildren.add(
                    CategoryAdapter.Companion.Item(
                        CategoryAdapter.Child,
                        "업체1", false
                    )
                )
                invisibleChildren.add(
                    CategoryAdapter.Companion.Item(
                        CategoryAdapter.Child,
                        "업체2", false
                    )
                )
            }
        categoryItems.add(places)
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "먹이용품 판매", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "유기동물 가족만들기", false).apply {
                className = "com.example.toy_project.ui.stray.StrayActivity"
            })

        view?.showCategories(categoryItems)
    }



}