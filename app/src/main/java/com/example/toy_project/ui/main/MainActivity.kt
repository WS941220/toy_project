package com.example.toy_project.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.toy_project.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ok_btn.setOnClickListener {
            oksetOnclickListener()
        }
    }

    private fun oksetOnclickListener() {
        val usrid = id_test.text.toString()
        val usrpw = pw_test.text.toString()

        presenter.okBtn(usrid, usrpw)
    }
}
