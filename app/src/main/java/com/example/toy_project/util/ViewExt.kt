package com.example.toy_project.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.toy_project.R
import java.text.SimpleDateFormat

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun Activity.progressOn(message: String) {
    ProgressDialog().progressON(this, "")
}

fun Activity.progressOff() {
    ProgressDialog().progressOFF()
}


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("SimpleDateFormat")
fun View.dateFormat(date: String?): String {
    val nowFormat = SimpleDateFormat("yyyyMMdd").parse(date)
    return SimpleDateFormat("yyyy.MM.dd").format(nowFormat)
}