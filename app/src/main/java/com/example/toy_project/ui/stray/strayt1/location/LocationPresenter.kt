package com.example.toy_project.ui.stray.strayt1.location

import android.annotation.SuppressLint
import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.StrayService
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.SettingPreference
import kotlinx.coroutines.*
import java.lang.Exception
import java.net.URLDecoder
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@SuppressLint("CheckResult")
@ActivityScoped
class LocationPresenter @Inject constructor(
    private var job: Job,
    private val strayService: StrayService,
    private val preference: SettingPreference
) : BasePresenter<LocationContract.LocationView?>(),
    LocationContract.LocationPresenter, CoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    override fun subscribe() {
        job = Job()
    }

    override fun unsubscribe() {
        super.unsubscribe()
    }

    override fun attach(view: LocationContract.LocationView) {
        this.view = view
    }

    override fun getUpr() {
        view?.showProgress("")

        launch {
            try {
                strayService.getUprList(
                    URLDecoder.decode(preference.getStrayKey(), "UTF-8")
                ).let {
                    withContext(Dispatchers.Main) {
                        view?.showUprList(it.body[0].items[0].item)
                        view?.closeProgress()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.closeProgress()
                }
            }
        }
    }


    override fun getOrg(upr_cd: String) {
        preference.setStrayUpr(upr_cd)
        preference.setStrayOrg("")
        preference.setStrayCare("")
        view?.showProgress("")

        launch {
            try {
                strayService.getOrgList(
                    URLDecoder.decode(upr_cd, "UTF-8"),
                    URLDecoder.decode(preference.getStrayKey(), "UTF-8")
                ).let {
                    withContext(Dispatchers.Main) {
                        view?.showOrgList(it.body[0].items[0].item)
                        view?.closeProgress()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.closeProgress()
                }
            }
        }
    }

    override fun getCare(upr_cd: String, org_cd: String) {
        preference.setStrayUpr(upr_cd)
        preference.setStrayOrg(org_cd)
        preference.setStrayCare("")
        view?.showProgress("")

        launch {
            try {
                strayService.getCareList(
                    URLDecoder.decode(upr_cd, "UTF-8"),
                    URLDecoder.decode(org_cd, "UTF-8"),
                    URLDecoder.decode(preference.getStrayKey(), "UTF-8")
                ).let {
                    withContext(Dispatchers.Main) {
                        view?.showCareList(it.body[0].items[0].item)
                        view?.closeProgress()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.closeProgress()
                }
            }
        }
    }

    override fun setLocation(upr_cd: String, org_cd: String, care_reg_no: String) {
        preference.setStrayUpr(upr_cd)
        preference.setStrayOrg(org_cd)
        preference.setStrayCare(care_reg_no)
    }


}