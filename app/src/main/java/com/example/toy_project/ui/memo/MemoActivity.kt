package com.example.toy_project.ui.memo

import android.os.Bundle
import com.example.memo_line.util.replaceFragmentInActivity
import com.example.memo_line.util.setupActionBar
import com.example.toy_project.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.shared_toolbar.*

class MemoActivity : DaggerAppCompatActivity() {

//    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        /**
         * 툴바
         */
        setupActionBar(R.id.toolbar) {
            setDisplayShowCustomEnabled(false)
            setCustomView(R.layout.actionbar_checkbox)
            setTitle(R.string.title)
            // 네비 on
//            setHomeAsUpIndicator(R.drawable.ic_menu)
//            setDisplayHomeAsUpEnabled(true)
        }

//        /**
//         * 네비게이션 on
//         */
//        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
//            setStatusBarBackground(R.color.colorPrimaryDark)
//        }
//        setupDrawerContent(findViewById(R.id.nav_view))

        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as MemoFragment? ?: MemoFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

    }

//    /**
//     * 네비 오픈
//     */
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            drawerLayout.openDrawer(GravityCompat.START)
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//    /**
//    * 네비 close
//    */
//    private fun setupDrawerContent(navigationView: NavigationView) {
//        navigationView.setNavigationItemSelectedListener { menuItem ->
//            menuItem.isChecked = true
//            drawerLayout.closeDrawers()
//            true
//        }
//    }

    /**
     * 뒤로가기 버튼 이벤트
     */
    override fun onBackPressed() {
        if (toolbar.title.equals(getString(R.string.nothing))) {
            val mainFg = supportFragmentManager.findFragmentById(R.id.contentFrame)
                    as MemoFragment
            mainFg.showMain()
        }
        else {
            super.onBackPressed()
        }
    }


}

