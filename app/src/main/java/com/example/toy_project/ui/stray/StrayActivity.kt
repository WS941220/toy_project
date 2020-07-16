package com.example.toy_project.ui.stray

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.toy_project.R

import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.stray.strayt1.Strayt1Fragment
import com.example.toy_project.ui.stray.strayt2.Strayt2Fragment
import com.example.toy_project.util.setupActionBar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_stray.*
import kotlinx.android.synthetic.main.activity_stray.tabs
import kotlinx.android.synthetic.main.shared_toolbar.*
import javax.inject.Inject

@ActivityScoped
class StrayActivity(
    private val tabLabel: Array<Int> = arrayOf(
        R.string.tab_strayt1,
        R.string.tab_strayt2
    )
) : DaggerAppCompatActivity(), StrayContract.StrayView {

    @Inject
    lateinit var presenter: StrayContract.StrayPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stray)

        /**
         * 툴바
         */
        setupActionBar(R.id.toolbar) {
            toolbarTitle.text = "유기동물 가족만들기"
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        pagers.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabs, pagers) { tab, position ->
            tab.text = resources.getString(tabLabel[position])
            when (position) {
                0 -> tab.customView = changeTab(position, null, true)
                else -> tab.customView = changeTab(position, null, false)
            }
        }.attach()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView =
                    changeTab(tab!!.position, tab.customView as LinearLayout, true)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView =
                    changeTab(tab!!.position, tab.customView as LinearLayout, false)
            }
        })

    }

    @SuppressLint("InflateParams")
    private fun changeTab(
        it: Int,
        tab: LinearLayout?,
        check: Boolean
    ): LinearLayout {
        val customTab: LinearLayout =
            tab ?: LayoutInflater.from(this).inflate(R.layout.tab_stray, null) as LinearLayout
        val tab_label = customTab.findViewById(R.id.nav_label) as TextView
        tab_label.text = resources.getString(tabLabel[it])

        when (check) {
            true -> {
                tab_label.setTextColor(ContextCompat.getColor(baseContext, R.color.primary));
            }
            false -> {
                tab_label.setTextColor(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.colorPrimaryDark
                    )
                )
            }
        }
        return customTab
    }

    private inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> Strayt1Fragment()
                1 -> Strayt2Fragment()
                else -> Strayt1Fragment()
            }
        }

        override fun getItemCount(): Int = tabLabel.size
    }
}