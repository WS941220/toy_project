package com.example.toy_project.ui.stray.strayt1.calendar

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.MainActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.android.support.DaggerFragment
import javax.inject.Inject

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



    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.dateRangePicker()
        val constraintsBuilder = CalendarConstraints.Builder()
        try {
            builder.setCalendarConstraints(constraintsBuilder.build())
            val picker: MaterialDatePicker<*> = builder.build()
            getDateRange(picker)
            picker.show(childFragmentManager, picker.toString())
        } catch (e: IllegalArgumentException) {

        }
    }

    private fun getDateRange(materialCalendarPicker: MaterialDatePicker<out Any>) {
        materialCalendarPicker.addOnPositiveButtonClickListener(
            { selection: Any? ->
//                Log.e("DateRangeText",materialCalendarPicker.headerText)

            })
        materialCalendarPicker.addOnNegativeButtonClickListener(
            { dialog: View? ->
            }
        )
        materialCalendarPicker.addOnCancelListener(
            { dialog: DialogInterface? ->
            }
        )
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

}