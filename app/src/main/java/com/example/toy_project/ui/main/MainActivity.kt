package com.example.toy_project.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.memo_line.util.replaceFragmentInActivity
import com.example.toy_project.R
import com.example.toy_project.ui.main.adopt.AdoptFragment
import com.example.toy_project.ui.main.chat.ChatFragment
import com.example.toy_project.ui.main.home.HomeFragment
import com.example.toy_project.ui.main.profile.ProfileFragment
import com.example.toy_project.ui.main.talk.TalkFragment
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
    )
) : DaggerAppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.findFragmentById(R.id.container)
//                as HomeFragment? ?: HomeFragment.newInstance().also {
//            addFragmentToActivity(it, "")
//        }

        (0..4).forEach {
            tabs.addTab(tabs.newTab().setText(resources.getString(navLabels[it])))
        }

        (0 until tabs.tabCount).forEach {
            when (it) {
                0 -> tabs.getTabAt(it)?.customView = changeTab(it, null, true)
                else -> tabs.getTabAt(it)?.customView = changeTab(it, null, false)
            }
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView = changeTab(tab!!.position, tab.customView as LinearLayout, true)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView = changeTab(tab!!.position, tab.customView as LinearLayout, false)
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun changeTab(it: Int, tab: LinearLayout?, check: Boolean): LinearLayout {
        val customTab: LinearLayout =
            tab ?: LayoutInflater.from(this).inflate(R.layout.nav_tab, null) as LinearLayout
        val tab_label = customTab.findViewById(R.id.nav_label) as TextView
        val tab_icon = customTab.findViewById(R.id.nav_icon) as ImageView
        tab_label.setText(resources.getString(navLabels[it]))

        if (check) {
            when (it) {
                0 -> supportFragmentManager.findFragmentById(R.id.container)
                    ?: HomeFragment.newInstance().also {
                        replaceFragmentInActivity(it, R.id.container)
                    }
                1 -> supportFragmentManager.findFragmentById(R.id.container)
                    ?: AdoptFragment.newInstance().also {
                        replaceFragmentInActivity(it, R.id.container)
                    }
                2 -> supportFragmentManager.findFragmentById(R.id.container)
                    ?: TalkFragment.newInstance().also {
                        replaceFragmentInActivity(it, R.id.container)
                    }
                3 -> supportFragmentManager.findFragmentById(R.id.container)
                    ?: ChatFragment.newInstance().also {
                        replaceFragmentInActivity(it, R.id.container)
                    }
                4 -> supportFragmentManager.findFragmentById(R.id.container)
                    ?: ProfileFragment.newInstance().also {
                        replaceFragmentInActivity(it, R.id.container)
                    }
            }

            tab_label.setTextColor(ContextCompat.getColor(baseContext, R.color.primary));
            tab_icon.setImageResource(navIconsActive[it])
        } else {
            tab_label.setTextColor(ContextCompat.getColor(baseContext, R.color.colorPrimaryDark));
            tab_icon.setImageResource(navIcons[it])
        }
        return customTab
    }
}

