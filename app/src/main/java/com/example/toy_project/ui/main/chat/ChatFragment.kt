package com.example.toy_project.ui.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import dagger.android.support.DaggerFragment
import javax.inject.Inject


@ActivityScoped
class ChatFragment : DaggerFragment(),
    ChatContract.ChatView {

    companion object {
        fun newInstance(): ChatFragment {
            return ChatFragment()
        }
    }

    @Inject
    lateinit var presenter: ChatContract.ChatPresenter
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
        rootView = inflater.inflate(R.layout.fragment_main_chat, container, false)
        with(rootView) {

        }
        return rootView
    }

}