package com.example.toy_project.ui.stray.strayt1.calendar

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.SettingPreference
import javax.inject.Inject


@ActivityScoped
class CalendarPresenter @Inject constructor(
    private val preference: SettingPreference
) : BasePresenter<CalendarContract.CalendarView?>(),
    CalendarContract.CalendarPresenter {

    override fun subscribe() {
    }
    override fun unsubscribe() {
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