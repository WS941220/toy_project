package com.example.toy_project.ui.stray.strayt1.calendar


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.applikeysolutions.cosmocalendar.model.Day
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.progressOff
import com.example.toy_project.util.progressOn
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_stray_calendar.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@ActivityScoped
class CalendarFragment : DaggerFragment(),
    CalendarContract.CalendarView {

    companion object {

        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }

    }

    @Inject
    lateinit var presenter: CalendarContract.CalendarPresenter
    private lateinit var rootView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe()
        presenter.attach(this)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        calendar_view.selectionManager = RangeSelectionManager(OnDaySelectedListener {
            var sDay = (calendar_view.selectedDates[0].get(Calendar.DATE)).toString()
            var sMonth = (calendar_view.selectedDates[0].get(Calendar.MONTH) + 1).toString()
            val sYear = (calendar_view.selectedDates[0].get(Calendar.YEAR)).toString()

            var eDay =
                (calendar_view.selectedDates[calendar_view.selectedDates.lastIndex].get(Calendar.DATE)).toString()
            var eMonth =
                (calendar_view.selectedDates[calendar_view.selectedDates.lastIndex].get(Calendar.MONTH) + 1).toString()
            val eYear =
                (calendar_view.selectedDates[calendar_view.selectedDates.lastIndex].get(Calendar.YEAR)).toString()

            sDay = if (Integer.parseInt(sDay) < 10) "0".plus(sDay) else sDay
            eDay = if (Integer.parseInt(eDay) < 10) "0".plus(eDay) else eDay
            sMonth = if (Integer.parseInt(sMonth) < 10) "0".plus(sMonth) else sMonth
            eMonth = if (Integer.parseInt(eMonth) < 10) "0".plus(eMonth) else eMonth

            presenter.setDate(sYear.plus(sMonth).plus(sDay), eYear.plus(eMonth).plus(eDay))
        })

        val sDate = SimpleDateFormat("yyyyMMdd").parse(
            (presenter.getDate()["sDate"])
        )
        val eDate = SimpleDateFormat("yyyyMMdd").parse(
            (presenter.getDate()["eDate"])
        )
        calendar_view.selectionManager.toggleDay(Day(sDate))
        calendar_view.selectionManager.toggleDay(Day(eDate))
        calendar_view.update()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_stray_calendar, container, false)
        with(rootView) {

        }


        return rootView
    }

    override fun showProgress(msg: String) {
        activity?.progressOn(msg)
    }

    override fun closeProgress() {
        activity?.progressOff()
    }

}