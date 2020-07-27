package com.example.toy_project.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.MainContract
import com.example.toy_project.util.progressOff
import com.example.toy_project.util.progressOn
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class HomeFragment : DaggerFragment(),
    HomeContract.HomeView {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    @Inject
    lateinit var presenter: HomeContract.HomePresenter
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
        rootView = inflater.inflate(R.layout.fragment_main_home, container, false)
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