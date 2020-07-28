package com.example.toy_project.ui.stray.strayt1.location

import android.annotation.SuppressLint
import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.StrayService
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.SettingPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.net.URLDecoder
import javax.inject.Inject

@SuppressLint("CheckResult")
@ActivityScoped
class LocationPresenter @Inject constructor(
    private val disposables: CompositeDisposable,
    private val strayService: StrayService,
    private val preference: SettingPreference
) : BasePresenter<LocationContract.LocationView?>(),
    LocationContract.LocationPresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: LocationContract.LocationView) {
        this.view = view
    }

    override fun getUpr() {
        view?.showProgress("")
        strayService.getUprList(
            URLDecoder.decode(preference.getStrayKey(), "UTF-8")
        ).subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribeBy(
            onNext = {
                view?.showUprList(it.body[0].items[0].item)
                view?.closeProgress()
            },
            onError = {
                view?.closeProgress()
            }
        )
    }


    override fun getOrg(upr_cd: String) {
        view?.showProgress("")
        strayService.getOrgList(
            URLDecoder.decode(upr_cd, "UTF-8"),
            URLDecoder.decode(preference.getStrayKey(), "UTF-8")
        ).subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribeBy(
            onNext = {
                view?.showOrgList(it.body[0].items[0].item)
                view?.closeProgress()
            },
            onError = {
                view?.closeProgress()
            }
        )
    }

    override fun getCare(upr_cd: String, org_cd: String) {
        view?.showProgress("")
        strayService.getCareList(
            URLDecoder.decode(upr_cd, "UTF-8"),
            URLDecoder.decode(org_cd, "UTF-8"),
            URLDecoder.decode(preference.getStrayKey(), "UTF-8")
        ).subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribeBy(
            onNext = {
                view?.showCareList(it.body[0].items[0].item)
                view?.closeProgress()
            },
            onError = {
                view?.closeProgress()
            }
        )
    }


}