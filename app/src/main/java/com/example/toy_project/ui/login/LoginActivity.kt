package com.example.toy_project.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.toy_project.R
import com.example.toy_project.ui.stray.StrayActivity
import com.example.toy_project.util.onThrottleClick
import com.example.toy_project.util.progressOff
import com.example.toy_project.util.progressOn
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.kakao.sdk.common.KakaoSdk
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity : DaggerAppCompatActivity(), LoginContract.View {
    companion object {
        const val RC_SIGN_IN = 9001
    }

    @Inject
    lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter.attach(this)
        presenter.google(baseContext)

        var usrid: String
        var usrpw: String

//        btnLogin.setOnClickListener {
//            usrid = etEmail.text.toString()
//            usrpw = etPassword.text.toString()
//            presenter.okBtn(usrid, usrpw, baseContext)
//        }
//        pass_btn.setOnClickListener {
//            presenter.passBtn(baseContext)
//        }

        main_btn.onThrottleClick {
            presenter.mainBtn(baseContext)
        }
        google_Btn.onThrottleClick {
            presenter.googleLogin()
        }
        kakao_Btn.onThrottleClick {
            presenter.kakaoLogin(baseContext)
        }
        apitest_btn.onThrottleClick {
            Intent(baseContext, StrayActivity::class.java).apply {
                baseContext.startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        googleLogin(account)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                presenter.googleLogin(task)
            }
        }
    }

    override fun googleLogin(intent: Intent) {
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun googleLogin(account: GoogleSignInAccount?) {
        Toast.makeText(this, account?.email, Toast.LENGTH_LONG).show()
    }

    override fun showProgress(msg: String) {
        this.progressOn(msg)
    }

    override fun closeProgress() {
        this.progressOff()
    }

}