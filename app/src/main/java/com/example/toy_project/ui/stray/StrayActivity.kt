package com.example.toy_project.ui.stray

import android.os.Bundle
import com.example.toy_project.R
import com.example.toy_project.ui.main.MainContract
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class StrayActivity  : DaggerAppCompatActivity(), StrayContract.StrayView {

    @Inject
    lateinit var presenter: StrayContract.StrayPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stray)
    }
}