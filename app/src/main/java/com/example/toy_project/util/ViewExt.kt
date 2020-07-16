package com.example.toy_project.util

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.toy_project.R

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

@SuppressLint("InflateParams")
fun setToolbarTitle(context: Context, title: String) : View {
    val customTab = LayoutInflater.from(context).inflate(R.layout.shared_toolbar_title, null)
    val toolbarTitle = customTab.findViewById(R.id.toolbarTitle) as TextView
    toolbarTitle.text = title

    return customTab
}
