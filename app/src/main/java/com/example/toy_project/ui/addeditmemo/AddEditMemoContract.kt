package com.example.toy_project.ui.addeditmemo


import android.content.Intent
import android.widget.EditText
import com.example.toy_project.base.BaseContract


interface AddEditMemoContract {
    interface View: BaseContract.View {
        var isShow: Boolean

        var isEdit: Boolean

        fun setTitle(title: String)

        fun onShow()

        fun onEdit()

        fun setContent(content: String)

        fun setImages(images: List<String>)

        fun showMessage(msg: String)

        fun showEmptyMemo()

        fun showMemosList()

        fun showMemosDeleted()

        fun showFilteringPopUpMenu()

        fun showGallery()

        fun showCamera()

        fun showUrl()

        fun showSuccessGallery(data: Intent?)

        fun showSuccessCamera()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun showMemo()

        fun showMessage(msg: String)

        fun result(requestCode: Int, resultCode: Int, data: Intent?)

        fun saveMemo(title: String, content: String, image: List<String>)

        fun deleteMemo(memoId: String)

        fun callGallery()

        fun callCamera()

        fun callUrl()
    }
}


