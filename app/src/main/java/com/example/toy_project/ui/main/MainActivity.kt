package com.example.toy_project.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.toy_project.R
import com.example.toy_project.ui.main.adopt.AdoptFragment
import com.example.toy_project.ui.main.chat.ChatFragment
import com.example.toy_project.ui.main.home.HomeFragment
import com.example.toy_project.ui.main.profile.ProfileFragment
import com.example.toy_project.ui.main.talk.TalkFragment
import com.example.toy_project.util.*
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity(
    private val navIcons: Array<Int> = arrayOf(
        R.drawable.ic_home,
        R.drawable.ic_adopt,
        R.drawable.ic_talk,
        R.drawable.ic_chat,
        R.drawable.ic_profile
    ),
    private val navLabels: Array<Int> = arrayOf(
        R.string.tab_home,
        R.string.tab_adopt,
        R.string.tab_talk,
        R.string.tab_chat,
        R.string.tab_profile
    ),
    private val navIconsActive: Array<Int> = arrayOf(
        R.drawable.ic_home_se,
        R.drawable.ic_adopt_se,
        R.drawable.ic_talk_se,
        R.drawable.ic_chat_se,
        R.drawable.ic_profile_se
    ),
    private val navFragments: Array<Any> = arrayOf(
        HomeFragment,
        AdoptFragment,
        TalkFragment,
        ChatFragment,
        ProfileFragment
    )
) : DaggerAppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 툴바
         */
        setupActionBar(R.id.toolbar) {
            setTitle(R.string.nothing)
            // 네비 on
//            setHomeAsUpIndicator(R.drawable.ic_menu)
//            setDisplayHomeAsUpEnabled(true)
        }

        (0 until navLabels.count()).forEach {
            tabs.addTab(tabs.newTab().setText(resources.getString(navLabels[it])))
        }

        (0 until tabs.tabCount).forEach {
            when (it) {
                0 -> tabs.getTabAt(it)?.customView = changeTab(it, null, check = true, first = true)
                else -> tabs.getTabAt(it)?.customView = changeTab(
                    it, null,
                    check = false,
                    first = false
                )
            }
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView =
                    changeTab(
                        tab!!.position, tab.customView as LinearLayout,
                        check = true,
                        first = false
                    )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView =
                    changeTab(
                        tab!!.position, tab.customView as LinearLayout,
                        check = false,
                        first = false
                    )
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun changeTab(
        it: Int,
        tab: LinearLayout?,
        check: Boolean,
        first: Boolean
    ): LinearLayout {
        val customTab: LinearLayout =
            tab ?: LayoutInflater.from(this).inflate(R.layout.tab_main, null) as LinearLayout
        val tab_label = customTab.findViewById(R.id.nav_label) as TextView
        val tab_icon = customTab.findViewById(R.id.nav_icon) as ImageView
        tab_label.text = resources.getString(navLabels[it])

        when (first) {
            true -> supportFragmentManager.findFragmentById(R.id.container)
                ?: HomeFragment.newInstance().also {
                    addFragmentToActivity(it, "MAIN")
                }
        }

        when (check) {
            true -> {
                navFragments[it].also { fragment ->
                    when (fragment) {
                        HomeFragment -> HomeFragment.newInstance().also {
                            replaceFragmentInActivity(
                                it,
                                R.id.container
                            )
                        }
                        AdoptFragment -> AdoptFragment.newInstance().also {
                            replaceFragmentInActivity(
                                it,
                                R.id.container
                            )
                        }
                        TalkFragment -> TalkFragment.newInstance().also {
                            replaceFragmentInActivity(
                                it,
                                R.id.container
                            )
                        }
                        ChatFragment -> ChatFragment.newInstance().also {
                            replaceFragmentInActivity(
                                it,
                                R.id.container
                            )
                        }
                        ProfileFragment -> ProfileFragment.newInstance().also {
                            replaceFragmentInActivity(
                                it,
                                R.id.container
                            )
                        }
                    }
                }
                tab_label.setTextColor(ContextCompat.getColor(baseContext, R.color.primary));
                tab_icon.setImageResource(navIconsActive[it])
            }
            false -> {
                tab_label.setTextColor(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.colorPrimaryDark
                    )
                )
                tab_icon.setImageResource(navIcons[it])
            }
        }
        return customTab
    }

    override fun showProgress(msg: String) {
        this.progressOn(msg)
    }

    override fun closeProgress() {
        this.progressOff()
    }
}

