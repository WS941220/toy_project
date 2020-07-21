package com.example.toy_project.util

import android.app.Activity
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.example.toy_project.R


class ProgressDialog() {
    companion object {
        var progressDialog: AppCompatDialog? = null
    }

    fun progressON(activity: Activity?, message: String?) {

        if (activity == null || activity.isFinishing) {
            return
        }
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressSET(message)
        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog?.setCancelable(false)
            progressDialog?.setContentView(R.layout.shared_progressbar)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            progressDialog?.show()
        }
        val img_loading_frame: ImageView =
            progressDialog?.findViewById<ImageView>(R.id.iv_frame_loading) as ImageView
        val frameAnimation =
            img_loading_frame.background as AnimationDrawable
        img_loading_frame.post(Runnable { frameAnimation.start() })
        val tv_progress_message =
            progressDialog?.findViewById<TextView>(R.id.tv_progress_message) as TextView
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.text = message
        }
    }

    fun progressSET(message: String?) {
        if (progressDialog == null || !progressDialog?.isShowing!!) {
            return
        }
        val tv_progress_message =
            progressDialog?.findViewById<TextView>(R.id.tv_progress_message) as TextView
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.text = message
        }
    }

    fun progressOFF() {
        progressDialog?.dismiss()
    }

}


