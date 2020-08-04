package com.example.toy_project.ui.show_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.toy_project.R
import com.example.toy_project.ui.main.talk.TalkFragment
import com.example.toy_project.util.setupActionBar
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_full_screen_img.*

class ShowListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        val part = intent.getStringExtra("part")
        val title = intent.getStringExtra("title")

        setupActionBar(R.id.toolbar) {
            when (part) {
                TalkFragment.ARGUMENT_TALK -> {
                    setCustomView(R.layout.actionbar_category)
                    setDisplayShowCustomEnabled(true)
                    setTitle(R.string.nothing)
                }
            }
        }



    }

}