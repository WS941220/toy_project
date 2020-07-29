package com.example.toy_project.util

import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        add(fragment, tag)
    }
}

fun Fragment.replaceFragmentInFragment(fragment: Fragment, @IdRes frameId: Int) {
    childFragmentManager.transact {
        replace(frameId, fragment)
        addToBackStack(null)
    }
}

fun Fragment.addFragmentToFragment(fragment: Fragment, tag: String) {
    childFragmentManager.transact {
        add(fragment, tag)
    }
}



/**
 * 툴바 셋팅
 */
fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}
