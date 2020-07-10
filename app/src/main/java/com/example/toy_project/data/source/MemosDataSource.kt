package com.example.toy_project.data.source

import com.example.toy_project.di.model.Memo


interface MemosDataSource {

    interface LoadMemosCallback {
        fun onMemosLoaded(memos: List<Memo>)
        fun onDataNotAvailable()
    }

    interface GetMemoCallback {
        fun onMemoLoaded(memo: Memo)
    }

    fun getMemos(callback: LoadMemosCallback)

    fun insertMemo(memo: Memo)

    fun checkAllMemo()

    fun cancelAllMemo()

    fun checkedMemo(checkedMemo: Memo)

    fun canceledMemo(canceledMemo: Memo)

    fun deleteCheckedMemos()

    fun deleteMemo(memoId: String)

    fun refreshMemos()

    fun getMemo(memoId: String, callback: GetMemoCallback)

}