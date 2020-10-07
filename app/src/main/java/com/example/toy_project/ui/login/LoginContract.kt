package com.example.toy_project.ui.login

import android.content.Context
import android.content.Intent
import com.example.toy_project.base.BaseContract
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

interface LoginContract {
    interface View: BaseContract.View {
        fun googleLogin(intent: Intent)
        fun updateUI(account: GoogleSignInAccount?)
    }
    interface Presenter : BaseContract.Presenter<View> {
        fun google(baseContext: Context)
        fun googleLogin()
        fun googleLogin(task: Task<GoogleSignInAccount>)
        fun kakaoLogin(baseContext: Context)
        fun okBtn(id: String, pw: String, baseContext: Context)
        fun passBtn(baseContext: Context)
        fun mainBtn(baseContext: Context)
    }
}