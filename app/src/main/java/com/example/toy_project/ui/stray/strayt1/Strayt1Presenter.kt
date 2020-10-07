package com.example.toy_project.ui.stray.strayt1

import android.annotation.SuppressLint
import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.StrayService
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.SettingPreference
import kotlinx.coroutines.*
import java.net.URLDecoder
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ActivityScoped
class Strayt1Presenter @Inject constructor(
    private var job: Job,
    private val strayService: StrayService,
    private val preference: SettingPreference
) : BasePresenter<Strayt1Contract.Strayt1View?>(),
    Strayt1Contract.Strayt1Presenter, CoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    override fun subscribe() {
        job = Job()
    }

    override fun unsubscribe() {
        super.unsubscribe()
        job.cancel()
    }

    override fun attach(view: Strayt1Contract.Strayt1View) {
        this.view = view
    }


    override fun getStrayList(num: Int) {
        view?.showProgress("")

        launch {
            try {
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
                ).let {
                    withContext(Dispatchers.Main) {
                        view?.showStrayList(it.body[0].items[0].item)
                        view?.setBottomTitle(preference.getStraySdate(), preference.getStrayEdate())
                        view?.closeProgress()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view?.setBottomTitle(preference.getStraySdate(), preference.getStrayEdate())
                    view?.closeProgress()
                }
            }
        }
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