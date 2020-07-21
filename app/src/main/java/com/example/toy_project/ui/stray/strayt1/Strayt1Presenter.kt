package com.example.toy_project.ui.stray.strayt1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.StrayService
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.Item
import com.example.toy_project.util.ProgressDialog
import com.example.toy_project.util.SettingPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.URLDecoder
import javax.inject.Inject

@ActivityScoped
class Strayt1Presenter @Inject constructor(
    private val disposables: CompositeDisposable,
    private val strayService: StrayService,
    private val preference: SettingPreference
) : BasePresenter<Strayt1Contract.Strayt1View?>(),
    Strayt1Contract.Strayt1Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: Strayt1Contract.Strayt1View) {
        this.view = view
    }

//    @SuppressLint("CheckResult")
//    override fun callApi(baseContext: Context) {
//        strayService.getSido(URLDecoder.decode(preference.getStrayKey(), "UTF-8")).subscribeOn(
//            Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribeBy(
//                onNext = {
//                    Toast.makeText(baseContext, "A", Toast.LENGTH_SHORT).show()
//                },
//
//                onError = {
//                    Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
//                }
//            )
//    }

    @SuppressLint("CheckResult")
    override fun getStrayList(baseContext: Context, activity: Activity, stray: Map<String, String>) {
        ProgressDialog().progressON(activity, "")
        strayService.getStrayList(
            URLDecoder.decode("20200621", "UTF-8"),
            URLDecoder.decode("20200721", "UTF-8"),
            URLDecoder.decode("429900", "UTF-8"),
            URLDecoder.decode("", "UTF-8"),
            URLDecoder.decode("", "UTF-8"),
            URLDecoder.decode("", "UTF-8"),
            URLDecoder.decode(stray["num"], "UTF-8"),
            URLDecoder.decode("20", "UTF-8"),
            URLDecoder.decode(preference.getStrayKey(), "UTF-8")
        ).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribeBy(
                onNext = {
                    view?.showStrayList(it.body[0].items[0].item)
                    ProgressDialog().progressOFF()
                },

                onError = {
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
                    ProgressDialog().progressOFF()
                }
            )
    }
}