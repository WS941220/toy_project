package com.example.toy_project.ui.stray.stray_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.toy_project.R
import com.example.toy_project.di.model.Item
import com.example.toy_project.ui.FullScreenImgActivity
import androidx.core.util.Pair
import com.example.toy_project.util.*
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_stray_detail.*
import kotlinx.android.synthetic.main.activity_stray_detail.kindTxt
import kotlinx.android.synthetic.main.activity_stray_detail.stateTxt
import kotlinx.android.synthetic.main.item_strayt1.*
import javax.inject.Inject

class StrayDetailActivity : AppCompatActivity() {

    lateinit var stray: Item

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stray_detail)

        setupActionBar(R.id.toolbar) {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        val intent = intent
        stray = intent.getSerializableExtra("stray") as Item
        val sSexCd = when (stray.sexCd) {
            "M" -> "수컷"
            "F" -> "암컷"
            else -> "미상"
        }
        stateTxt.text = stray.processState
        kindTxt.text = "${stray.kindCd} | ${stray.colorCd} | $sSexCd".replace("[기타축종] ", "")
        titleTxt.text = stray.noticeNo
        hdateTxt.text = Format.dateFormat(stray.happenDt!!)
        sdateTxt.text =
            "${Format.dateFormat(stray.noticeSdt!!)} ~ ${Format.dateFormat(stray.noticeEdt!!)}"
        locationTxt.text = stray.happenPlace
        ageTxt.text = stray.age
        kgTxt.text = stray.weight
        featureTxt.text = stray.specialMark
        careTxt.text = stray.careNm
        clocationTxt.text = stray.careAddr
        callTxt.text = stray.careTel

        when {
            stray.processState!!.contains("보호중") -> {
                stateTxt.setTextColor(ContextCompat.getColor(baseContext, R.color.green))
            }
            stray.processState!!.contains("종료") -> {
                stateTxt.setTextColor(ContextCompat.getColor(baseContext, R.color.red))
            }
            else -> {
                stateTxt.setTextColor(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.colorPrimaryDark
                    )
                )
            }
        }

        imageView.onThrottleClick { fullImage(stray.popfile!!) }
        callFab.onThrottleClick { callCare() }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun fullImage(image: String) {
        val img: Pair<View, String> =
            Pair.create(imageView, imageView.transitionName)
        val optionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, img)

        val intent = Intent(baseContext, FullScreenImgActivity::class.java).apply {
            putExtra("stray", stray.popfile)
            putExtra("uri", image)
        }
        startActivity(intent, optionsCompat.toBundle())
    }

    private fun callCare() {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${callTxt.text.toString().replace("","-")}")))
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        Glide.with(baseContext).load(stray.popfile).into(imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

}