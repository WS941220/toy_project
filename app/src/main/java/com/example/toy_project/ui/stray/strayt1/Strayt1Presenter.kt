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


    @SuppressLint("CheckResult")
    override fun getStrayList(num: Int) {
        view?.showProgress("")
        strayService.getStrayList(
            URLDecoder.decode(preference.getStraySdate(), "UTF-8"),
            URLDecoder.decode(preference.getStrayEdate(), "UTF-8"),
            URLDecoder.decode("429900", "UTF-8"),
            URLDecoder.decode(preference.getStrayUpr(), "UTF-8"),
            URLDecoder.decode(preference.getStrayOrg(), "UTF-8"),
            URLDecoder.decode(preference.getStrayCare(), "UTF-8"),
            URLDecoder.decode(num.toString(), "UTF-8"),
            URLDecoder.decode("20", "UTF-8"),
            URLDecoder.decode(preference.getStrayKey(), "UTF-8")
        ).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread()).onBackpressureBuffer().subscribeBy(
                onNext = {
                    view?.showStrayList(it.body[0].items[0].item)
                    view?.setBottomTitle(preference.getStraySdate(), preference.getStrayEdate())
                    view?.closeProgress()
                },
                onError = {
                    view?.setBottomTitle(preference.getStraySdate(), preference.getStrayEdate())
                    view?.closeProgress()
                }
            )
    }

    override fun setDefault(
        s_date: String,
        e_date: String,
        upr_cd: String,
        org_cd: String,
        care_reg_no: String
    ) {
        preference.setStraySdate(s_date)
        preference.setStrayEdate(e_date)
        preference.setStrayUpr(upr_cd)
        preference.setStrayOrg(org_cd)
        preference.setStrayCare(care_reg_no)
    }
}