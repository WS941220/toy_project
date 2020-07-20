package com.example.toy_project.util

import android.content.Context

private const val PUSH_ENABLE = "push_enable"
private const val NAME = "pref_setting"
private const val STRAY_KEY = "LnE79Xug3YnCqjQlhWbhYgsnvqFg%2FHdbuGGXi3ElPWAaaiBISuQr4j7l3JtaoTeZqNyD5uG0noW031U%2BiJzASA%3D%3D"
private const val USER_ID = "user_ID"


class SettingPreference(context: Context) : CachedPreference(context, NAME) {

    fun setPushEnable(enable: Boolean) = put(PUSH_ENABLE, enable)
    fun isPushEnable() = get(PUSH_ENABLE, false) as Boolean

    fun setUserID(userID: String) = put(USER_ID, userID);
    fun getUserID() = get(USER_ID, "") as String;

    fun getStrayKey() = get(STRAY_KEY, STRAY_KEY) as String
}