package com.example.toy_project.util

import android.content.Context

private const val PUSH_ENABLE = "push_enable"
private const val NAME = "pref_setting"
private const val STRAY_KEY = "0pt5JC%2BQEsWiyIgpG1CpJZEIOlYxRjo3X2js8WB66HJ6izbAjm6fNH0bk4DIhhV43mfJgxeTju5dtkrxY7dyFA%3D%3D"
private const val USER_ID = "user_ID"


class SettingPreference(context: Context) : CachedPreference(context, NAME) {

    fun setPushEnable(enable: Boolean) = put(PUSH_ENABLE, enable)
    fun isPushEnable() = get(PUSH_ENABLE, false) as Boolean

    fun setUserID(userID: String) = put(USER_ID, userID);
    fun getUserID() = get(USER_ID, "") as String;

    fun getStrayKey() = get(STRAY_KEY, STRAY_KEY) as String
}