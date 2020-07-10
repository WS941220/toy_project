package com.example.toy_project.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.ApiService
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.User
import com.example.toy_project.ui.memo.MemoActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ActivityScoped
class MainPresenter @Inject constructor(
    private val disposables: CompositeDisposable,
    private val apiService: ApiService
) : BasePresenter<MainContract.View?>(), MainContract.Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    @SuppressLint("CheckResult")
    override fun okBtn(id: String, pw: String, baseContext: Context) {
        val user = User(id, pw)

        apiService.authenticate(user).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribeBy(
                onNext = {
                    Toast.makeText(baseContext, it.token, Toast.LENGTH_SHORT).show()
                },

                onError = {
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                }
            )
    }

    override fun passBtn(baseContext: Context) {
        Intent(baseContext, MemoActivity::class.java).apply {
            baseContext.startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

}
