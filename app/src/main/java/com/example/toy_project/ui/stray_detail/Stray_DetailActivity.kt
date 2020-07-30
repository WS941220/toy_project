package com.example.toy_project.ui.stray_detail

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.toy_project.R
import com.example.toy_project.util.progressOff
import com.example.toy_project.util.progressOn
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_stray_detail.*
import javax.inject.Inject


class Stray_DetailActivity : DaggerAppCompatActivity(), Stray_DetailContract.View {

    @Inject
    lateinit var presenter: Stray_DetailContract.Presenter
    lateinit var img: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stray_detail)

        val intent = intent
        img = intent.getSerializableExtra("img") as String
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        Glide.with(baseContext).load(img).into(imageView01)
    }

    override fun showProgress(msg: String) {
        this.progressOn(msg)
    }

    override fun closeProgress() {
        this.progressOff()
    }

}