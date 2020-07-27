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

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun Activity.progressOn(message: String) {
    ProgressDialog().progressON(this, "")
}

fun Activity.progressOff() {
    ProgressDialog().progressOFF()
}