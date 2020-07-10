package com.example.toy_project.ui.memo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memo_line.util.showSnackBar
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.Memo
import com.example.toy_project.ui.addeditmemo.AddEditMemoActivity
import com.example.toy_project.ui.addeditmemo.AddEditMemoFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class MemoFragment : DaggerFragment(), MemoContract.View, MemoAdapter.MemoItemListener {

    companion object {
        fun newInstance(): MemoFragment {
            return MemoFragment()
        }
    }

    @Inject
    lateinit var presenter: MemoContract.Presenter

    override var isDelete: Boolean = false

    private lateinit var rootView: View
    private lateinit var action_check: CheckBox
    private lateinit var noMemo: TextView
    private lateinit var mainRecycler: RecyclerView

    private val mainItem = ArrayList<Memo>(0)

    private lateinit var memoAdapter: MemoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memoAdapter = MemoAdapter(context, mainItem, View.GONE, false, this)

    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ mainRecycler.scrollToPosition(mainItem.size - 1) }, 200)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_memo, container, false)

        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_memo).apply {
            setImageResource(R.drawable.ic_create)
            setOnClickListener { presenter.addNewMemo() }
        }

        with(rootView) {
            mainRecycler = findViewById(R.id.mainRecycler)
            action_check =
                (activity as AppCompatActivity).supportActionBar?.customView?.findViewById(
                    R.id.action_check
                ) as CheckBox

            noMemo = findViewById(R.id.noMemo)
        }

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.reverseLayout = true
        mLayoutManager.stackFromEnd = true

        mainRecycler.layoutManager = mLayoutManager
        mainRecycler.adapter = memoAdapter

        setHasOptionsMenu(true)

        return rootView
    }


    /**
     * 메뉴 inflate
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (!isDelete) {
            inflater.inflate(R.menu.toolbar_fragment_menu, menu)
        } else {
            inflater.inflate(R.menu.done_menu, menu)
        }
    }

    /**
     * 툴바 메뉴 선택
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> showCheckBox()
            R.id.menu_success -> showMain()
        }
        return true
    }

    /**
     * 스낵바 메시지
     */
    override fun showMessage(msg: String) {
        view?.showSnackBar(msg, Snackbar.LENGTH_LONG)
    }

    /**
     * 편집 클릭 체크박스 활성화
     */
    fun showCheckBox() {
        isDelete = true
        requireActivity().invalidateOptionsMenu();
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowCustomEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.nothing)
        action_check.setOnCheckedChangeListener { v, isCecked ->
            memoAdapter.check = isCecked
            memoAdapter.notifyDataSetChanged()
        }

        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_memo).apply {
            setImageResource(R.drawable.ic_delete)
            setOnClickListener { presenter.deleteCheckedMemos() }
        }
        memoAdapter.visible = View.VISIBLE
        memoAdapter.notifyDataSetChanged()
    }

    /**
     * 메인으로
     */
    fun showMain() {
        isDelete = false
        action_check.isChecked = false
        requireActivity().invalidateOptionsMenu();
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.title)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowCustomEnabled(false)
        requireActivity().findViewById<FloatingActionButton>(R.id.fab_add_memo).apply {
            setImageResource(R.drawable.ic_create)
            setOnClickListener { presenter.addNewMemo() }
        }
        memoAdapter.visible = View.GONE
        memoAdapter.notifyDataSetChanged()

    }

    /**
     * 메모 저장 메세지
     */
    override fun showSuccessfullySavedMessage() {
        presenter.showMessage(getString(R.string.save_memo))
    }

    /**
     * 전체 선택
     */
    override fun onCheckAllMemos() {
        presenter.onCheckAllMemos()
    }

    /**
     * 전체 선택 취소
     */
    override fun onCancelAllMemos() {
        presenter.onCancelAllMemos()
    }

    /**
     * 메모 선택
     */
    override fun onCheckMemoClick(checkMemo: Memo) {
        presenter.checkedMemo(checkMemo)
    }

    /**
     * 선택 취소
     */
    override fun onCancelMemoClick(cancelMemo: Memo) {
        presenter.canceledMemo(cancelMemo)
    }

    /**
     * 선택된 메모 삭제
     */
    override fun showDeleteMemos() {
        showMain()
        presenter.showMessage(getString(R.string.remove_memo))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode)
    }


    override fun showMemos(memos: List<Memo>) {
        mainRecycler.visibility = View.VISIBLE
        noMemo.visibility = View.GONE
        mainItem.clear()
        mainItem.addAll(memos)
        memoAdapter.notifyDataSetChanged()
    }

    override fun showNoMemos() {
        noMemo.visibility = View.VISIBLE
        mainRecycler.visibility = View.GONE
    }

    /**
     * 메모 작성
     */
    override fun showAddMemo() {
        val intent = Intent(context, AddEditMemoActivity::class.java)
        startActivityForResult(intent, AddEditMemoActivity.REQUEST_ADD_MEMO)
    }


    override fun onMemoClick(clickMemo: Memo) {
        presenter.openMemo(clickMemo)
    }

    /**
     * 메모 보기
     */
    override fun showOpenMemo(memoId: String) {
        val intent = Intent(context, AddEditMemoActivity::class.java).apply {
            putExtra(AddEditMemoFragment.ARGUMENT_SHOW_MEMO_ID, memoId)
        }
        startActivity(intent)
    }

}
