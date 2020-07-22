package com.example.toy_project.ui.stray.strayt1.calendar


import com.example.toy_project.base.BaseContract

interface CalendarContract {

    interface CalendarView : BaseContract.View {

    }

    interface CalendarPresenter : BaseContract.Presenter<CalendarView> {

    }
}