package com.example.toy_project.ui.main.home

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.MainContract
import com.example.toy_project.util.CategoryAdapter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class TalkPresenter @Inject constructor(
    private val disposables: CompositeDisposable
) : BasePresenter<TalkContract.TalkView?>(),
    TalkContract.TalkPresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: TalkContract.TalkView) {
        this.view = view
    }

    override fun getCategories() {
        val categoryItems: MutableList<CategoryAdapter.Companion.Item> = arrayListOf()
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "팔로잉 최신 글", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "전체 글 보기", false).apply {
            })
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "소모임", true))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "파충류 소모임", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "양서류 소모임", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "절지류 소모임", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Child, "기타동물 소모임", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "자유로운 이야기", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "묻고 답해요", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "나눔/드림", false))
        categoryItems.add(
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "이용후기", false))
        val places: CategoryAdapter.Companion.Item =
            CategoryAdapter.Companion.Item(
                CategoryAdapter.Header, "상식",true).apply {
                invisibleChildren.add(
                    CategoryAdapter.Companion.Item(
                        CategoryAdapter.Child,
                        "파충류 상식", false
                    )
                )
                invisibleChildren.add(
                    CategoryAdapter.Companion.Item(
                        CategoryAdapter.Child,
                        "양서류 상식", false
                    )
                )
                invisibleChildren.add(
                    CategoryAdapter.Companion.Item(
                        CategoryAdapter.Child,
                        "절지류 상식", false
                    )
                )
                invisibleChildren.add(
                    CategoryAdapter.Companion.Item(
                        CategoryAdapter.Child,
                        "기타 상식", false
                    )
                )
            }
        categoryItems.add(places)

        view?.showCategories(categoryItems)
    }
}