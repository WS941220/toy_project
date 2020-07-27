package com.example.toy_project.ui.stray.strayt1.calendar

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.SettingPreference
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


@ActivityScoped
class CalendarPresenter @Inject constructor(
    private val disposables: CompositeDisposable,
    private val preference: SettingPreference
) : BasePresenter<CalendarContract.CalendarView?>(),
    CalendarContract.CalendarPresenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
        super.unsubscribe()
        disposables.clear()
        disposables.dispose()
    }

    override fun attach(view: CalendarContract.CalendarView) {
        this.view = view
    }

    override fun setDate(sDate: String, eDate: String) {
        preference.setStraySdate(sDate)
        preference.setStrayEdate(eDate)
    }

    override fun getDate(): MutableMap<String, String> {
        val date: MutableMap<String, String> = mutableMapOf()
        date["sDate"] = preference.getStraySdate()
        date["eDate"] = preference.getStrayEdate()

        return date
    }


}