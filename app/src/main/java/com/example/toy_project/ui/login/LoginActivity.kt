package com.example.toy_project.ui.login

import android.os.Bundle
import com.example.toy_project.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(), LoginContract.View {

    @Inject
    lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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

        main_btn.setOnClickListener {
            presenter.mainBtn(baseContext)
        }
    }

}