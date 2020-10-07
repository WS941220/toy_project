package com.example.toy_project.ui.login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.toy_project.base.BasePresenter
import com.example.toy_project.data.ApiService
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.User
import com.example.toy_project.ui.main.MainActivity
import com.example.toy_project.ui.memo.MemoActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.rx
import io.reactivex.Single
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ActivityScoped
class LoginPresenter @Inject constructor(
    private var job: Job,
    private val apiService: ApiService,
    private val gso: GoogleSignInOptions
) : BasePresenter<LoginContract.View?>(), LoginContract.Presenter, CoroutineScope {
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun subscribe() {
        job = Job()
    }

    override fun unsubscribe() {
        super.unsubscribe()
        job.cancel()
    }

    override fun attach(view: LoginContract.View) {
        this.view = view
    }

    override fun okBtn(id: String, pw: String, baseContext: Context) {
        val user = User(id, pw)

        launch {
            try {
                apiService.authenticate(user).let {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(baseContext, it.token, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                val msg = e.message
                withContext(Dispatchers.Main) {
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun google(baseContext: Context) {
        mGoogleSignInClient = GoogleSignIn.getClient(baseContext, gso)
    }

    override fun googleLogin() {
        view?.googleLogin(mGoogleSignInClient.signInIntent)
    }

    override fun googleLogin(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            view?.updateUI(account)
        } catch (e: ApiException) {
            view?.updateUI(null)
        }
    }

    override fun kakaoLogin(baseContext: Context) {
        launch {
            try {
                Single.just(LoginClient.instance.isKakaoTalkLoginAvailable(baseContext)).flatMap {
                    when (it) {
                        true -> LoginClient.rx.loginWithKakaoTalk(baseContext)
                        false -> LoginClient.rx.loginWithKakaoAccount(baseContext)
                    }
                }.let {
                    val test = it.blockingGet().accessToken
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            baseContext,
                            "로그인성공 $test",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun passBtn(baseContext: Context) {
        Intent(baseContext, MemoActivity::class.java).apply {
            baseContext.startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun mainBtn(baseContext: Context) {
        Intent(baseContext, MainActivity::class.java).apply {
            baseContext.startActivity(this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
}