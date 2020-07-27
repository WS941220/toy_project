package com.example.toy_project.util

import android.annotation.SuppressLint
import android.content.Context
import com.example.toy_project.ui.stray.strayt1.calendar.CalendarFragment
import java.text.SimpleDateFormat
import java.util.*

private const val PUSH_ENABLE = "push_enable"
private const val NAME = "pref_setting"
private const val STRAY_KEY =
    "0pt5JC%2BQEsWiyIgpG1CpJZEIOlYxRjo3X2js8WB66HJ6izbAjm6fNH0bk4DIhhV43mfJgxeTju5dtkrxY7dyFA%3D%3D"
private const val USER_ID = "user_ID"
private const val STRAY_SDATE = "stray_SDATE"
private const val STRAY_EDATE = "stray_EDATE"


class SettingPreference(context: Context) : CachedPreference(context, NAME) {
    private var cal: Calendar = Calendar.getInstance().apply {
        add(Calendar.DATE, -15)
    }

    fun setPushEnable(enable: Boolean) = put(PUSH_ENABLE, enable)
    fun isPushEnable() = get(PUSH_ENABLE, false) as Boolean

    fun setUserID(userID: String) = put(USER_ID, userID);
    fun getUserID() = get(USER_ID, "") as String

    fun getStrayKey() = get(STRAY_KEY, STRAY_KEY) as String

    fun setStraySdate(date: String) = put(STRAY_SDATE, date)
    @SuppressLint("SimpleDateFormat")
    fun getStraySdate() = get(
        STRAY_SDATE, SimpleDateFormat("yyyyMMdd").format(
            Date(cal.timeInMillis)
        )
    ) as String

    fun setStrayEdate(date: String) = put(STRAY_EDATE, date)
    @SuppressLint("SimpleDateFormat")
    fun getStrayEdate() = get(
        STRAY_EDATE, SimpleDateFormat("yyyyMMdd").format(
            Date()
        )
    ) as String
}