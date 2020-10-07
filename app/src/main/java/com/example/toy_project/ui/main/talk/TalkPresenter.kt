package com.example.toy_project.ui.main.home

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.ApiService
import com.example.toy_project.di.Scoped.ActivityScoped
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ActivityScoped
class TalkPresenter @Inject constructor(
    private var job: Job,
    private val apiService: ApiService
) : BasePresenter<TalkContract.TalkView?>(),
    TalkContract.TalkPresenter, CoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    override fun subscribe() {
        job = Job()
    }

    override fun unsubscribe() {
        super.unsubscribe()
        job.cancel()
    }

    override fun attach(view: TalkContract.TalkView) {
        this.view = view
    }

    override fun getCategories() {
        launch {
            try {
                apiService.getCategory("Talk").apply {
                    withContext(Dispatchers.Main) {
                        view?.showCategories(this@apply)
                    }
                }
            } catch (e: Exception) {
                val msg = e.message.toString()
                withContext(Dispatchers.Main) {
                    view?.errorDialog(msg)
                }
            }
        }
    }
}