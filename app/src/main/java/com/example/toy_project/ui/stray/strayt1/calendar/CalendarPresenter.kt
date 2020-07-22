package com.example.toy_project.ui.stray.strayt1.calendar

import com.example.toy_project.base.BasePresenter
import com.example.toy_project.di.Scoped.ActivityScoped
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


@ActivityScoped
class CalendarPresenter @Inject constructor(
    private val disposables: CompositeDisposable
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


}