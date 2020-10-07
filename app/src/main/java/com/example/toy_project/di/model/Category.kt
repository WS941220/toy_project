package com.example.toy_project.di.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category constructor(
    @SerializedName("type") val type: Int,
    @SerializedName("text") val text: String,
    @SerializedName("child") val child: Boolean
) : Serializable {
    @SerializedName("invisibleChildren") var invisibleChildren: MutableList<Category> = arrayListOf()
    @SerializedName("className") var className: String = "com.example.toy_project.ui.show_list.ShowListActivity"
    @SerializedName("isClass") var isClass: Boolean = false
}