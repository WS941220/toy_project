package com.example.toy_project.ui.main.talk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.main.home.TalkContract
import dagger.android.support.DaggerFragment
import javax.inject.Inject


@ActivityScoped
class TalkFragment : DaggerFragment(),
    TalkContract.TalkView {

    companion object {
        fun newInstance(): TalkFragment {
            return TalkFragment()
        }
    }

    @Inject
    lateinit var presenter: TalkContract.TalkPresenter
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
        rootView = inflater.inflate(R.layout.fragment_main_talk, container, false)
        with(rootView) {

        }
        return rootView
    }

}