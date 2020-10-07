package com.example.toy_project.ui.main.adopt

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.ApiService
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.Category
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ActivityScoped
class AdoptPresenter @Inject constructor(
    private var job: Job,
    private val apiService: ApiService
) : BasePresenter<AdoptContract.AdoptView?>(),
    AdoptContract.AdoptPresenter, CoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    override fun subscribe() {
        job = Job()
    }

    override fun unsubscribe() {
        super.unsubscribe()
        job.cancel()
    }

    override fun attach(view: AdoptContract.AdoptView) {
        this.view = view
    }

    override fun getCategories() {
        launch {
            try {
                apiService.getCategory("Adopt").apply {
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