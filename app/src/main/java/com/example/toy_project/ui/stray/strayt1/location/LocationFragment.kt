package com.example.toy_project.ui.stray.strayt1.location

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.progressOff
import com.example.toy_project.util.progressOn
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_stray_location.*
import javax.inject.Inject

@ActivityScoped
class LocationFragment : DaggerFragment(),
    LocationContract.LocationView {

    companion object {
        fun newInstance(): LocationFragment {
            return LocationFragment()
        }
    }

    @Inject
    lateinit var presenter: LocationContract.LocationPresenter
    private lateinit var rootView: View
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var popupWindow: PopupWindow
    private lateinit var imageView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe()
        presenter.attach(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


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
        rootView = inflater.inflate(R.layout.fragment_stray_location, container, false)
        with(rootView) {
            imageView = findViewById(R.id.imageView)
        }

        imageView.setOnClickListener {
            popupWindow.dismiss()
            provideCountryPopupWindow(it)
            popupWindow.showAsDropDown(it, 0, -it.height)
        }

        return rootView
    }

    @SuppressLint("InflateParams")
    private fun provideCountryPopupWindow(it: View) {
        popupWindow = PopupWindow(it.width, ViewGroup.LayoutParams.WRAP_CONTENT)
            .apply {
                val backgroundDrawable = ContextCompat.getDrawable(
                    context!!, R.drawable.background_spinner_white)
                    .apply {  }
                setBackgroundDrawable(backgroundDrawable)
                isOutsideTouchable = true
                val listView = layoutInflater.inflate(
                    R.layout.item_location_dropdown,
                    null,
                    false) as ListView
                listView.adapter = locationAdapter
                listView.setOnItemClickListener { _, _, position, _ ->
                    val selectedCountry = locationAdapter.getItem(position)!!
                    popupWindow.dismiss()
                }
                contentView = listView
            }
    }

    override fun showProgress(msg: String) {
        activity?.progressOn(msg)
    }

    override fun closeProgress() {
        activity?.progressOff()
    }

}