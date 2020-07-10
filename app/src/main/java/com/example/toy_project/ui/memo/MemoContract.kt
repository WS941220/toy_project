package com.example.toy_project.ui.memo

import com.example.toy_project.base.BaseContract
import com.example.toy_project.di.model.Memo


interface MemoContract {
    interface View: BaseContract.View {
        var isDelete: Boolean

        fun showMessage(msg: String)

        fun showMemos(memos: List<Memo> )

        fun showNoMemos()

        fun showAddMemo()

        fun showDeleteMemos()

        fun showOpenMemo(memoId: String)

        fun showSuccessfullySavedMessage()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadMemos(forceUpdate: Boolean)

        fun result(requestCode: Int, resultCode: Int)

        fun showMessage(msg: String)

        fun addNewMemo()

        fun onCheckAllMemos()

        fun onCancelAllMemos()

        fun checkedMemo(checkedMemo: Memo)

        fun canceledMemo(canceledMemo: Memo)

        fun deleteCheckedMemos()

        fun deleteMemo(memoId: String)

        fun openMemo(clickMemo: Memo)
    }
}

