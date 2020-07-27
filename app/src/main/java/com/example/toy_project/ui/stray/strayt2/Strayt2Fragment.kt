package com.example.toy_project.ui.stray.strayt2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.util.progressOff
import com.example.toy_project.util.progressOn
import dagger.android.support.DaggerFragment
import javax.inject.Inject


@ActivityScoped
class Strayt2Fragment : DaggerFragment(),
    Strayt2Contract.Strayt2View {

    companion object {
        fun newInstance(): Strayt2Fragment {
            return Strayt2Fragment()
        }
    }

    @Inject
    lateinit var presenter: Strayt2Contract.Strayt2Presenter
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
        rootView = inflater.inflate(R.layout.fragment_stray_t2, container, false)
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