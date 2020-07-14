package com.example.toy_project.util

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class CheckPermission constructor(
    fragment: Fragment,
    permission: String,
    requestCode: Int
) {
    init {
        if (ContextCompat.checkSelfPermission(
                fragment.activity!!,
                permission
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (fragment.shouldShowRequestPermissionRationale(
                    permission
                )
            ) {
                fragment.requestPermissions(
                    arrayOf(permission), requestCode
                )

            } else {
                fragment.requestPermissions(
                    arrayOf(permission),
                    requestCode
                )
            }
        } else {
            fragment.onRequestPermissionsResult(requestCode, arrayOf(permission), IntArray(1) {PackageManager.PERMISSION_GRANTED})
        }
    }
}