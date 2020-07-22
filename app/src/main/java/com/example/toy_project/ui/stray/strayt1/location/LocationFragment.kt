package com.example.toy_project.ui.stray.strayt1.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import dagger.android.support.DaggerFragment
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe()
        presenter.attach(this)
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

        }
        return rootView
    }

}