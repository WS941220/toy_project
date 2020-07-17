package com.example.toy_project.ui.stray.strayt1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import dagger.android.support.DaggerFragment
import javax.inject.Inject


@ActivityScoped
class Strayt1Fragment : DaggerFragment(),
    Strayt1Contract.Strayt1View {

    companion object {
        fun newInstance(): Strayt1Fragment {
            return Strayt1Fragment()
        }
    }

    @Inject
    lateinit var presenter: Strayt1Contract.Strayt1Presenter
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
        rootView = inflater.inflate(R.layout.fragment_stray_t1, container, false)
        with(rootView) {
            presenter.callApi(this.context)
        }
        return rootView
    }

}