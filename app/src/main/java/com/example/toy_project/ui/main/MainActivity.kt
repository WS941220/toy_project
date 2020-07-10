package com.example.toy_project.ui.main

import android.content.Intent
import android.os.Bundle
import com.example.toy_project.R
import com.example.toy_project.ui.memo.MemoActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var usrid: String
        var usrpw: String

        ok_btn.setOnClickListener {
            usrid = id_test.text.toString()
            usrpw = pw_test.text.toString()

            presenter.okBtn(usrid, usrpw, baseContext)
        }
        pass_btn.setOnClickListener {
            presenter.passBtn(baseContext)
        }
    }

}
