package com.example.toy_project.ui.addeditmemo


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.FullScreenImgActivity
import com.example.toy_project.ui.gallery.GalleryActivity
import com.example.toy_project.util.CheckPermission
import com.example.toy_project.util.showSnackBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@ActivityScoped
class AddEditMemoFragment : DaggerFragment(), AddEditMemoContract.View,
    AddEditMemoAdapter.onItemClickListener {

    companion object {
        const val ARGUMENT_SHOW_MEMO_ID = "SHOW_MEMO_ID"
        const val PICK_GALLERY_ID = 1
        const val PICK_CAMERA_ID = 2

        fun newInstance(memoId: String?) =
            AddEditMemoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_SHOW_MEMO_ID, memoId)
                }
            }
    }

    override var isShow: Boolean = true
    override var isEdit: Boolean = false

    private lateinit var title: EditText
    private lateinit var content: EditText
    private lateinit var picRecyler: RecyclerView

    private var currentPhotoPath = ""
    private lateinit var photoURI: Uri

    private val picItem = ArrayList<String>(0)
    private val picItem2 = ArrayList<String>(0)
    private lateinit var picAdapter: AddEditMemoAdapter

    @Inject
    lateinit var presenter: AddEditMemoContract.Presenter

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe()
        presenter.attach(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.findViewById<FloatingActionButton>(R.id.fab_edit_memo_done)?.apply {
            setImageResource(R.drawable.ic_done)
            setOnClickListener {
                for (i in 0 until picItem.size) {
                    picItem2.add(picItem[i])
                }
                presenter.saveMemo(title.text.toString(), content.text.toString(), picItem2)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_add_edit_memo, container, false)
        picAdapter = AddEditMemoAdapter(context, picItem, View.VISIBLE, this)

        with(rootView) {
            picRecyler = findViewById(R.id.picRecyler)
            title = findViewById(R.id.add_memo_title)
            content = findViewById(R.id.add_memo_content)
        }
        picRecyler.layoutManager = GridLayoutManager(context, 3)
        picRecyler.adapter = picAdapter

        focusChange()
        setHasOptionsMenu(true)

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.result(requestCode, resultCode, data)
    }


    override fun setTitle(title: String) {
        this.title.setText(title)
    }

    override fun setContent(content: String) {
        this.content.setText(content)
    }

    override fun setImages(images: List<String>) {
        for (i in images.indices) {
            if (images[i] != "") {
                picItem.add(images[i])
            }
        }
        picAdapter.pics = picItem
        picAdapter.notifyDataSetChanged()
    }

    override fun onShow() {
        isShow = true
        isEdit = false
        title.isCursorVisible = false
        content.isCursorVisible = false

        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.nothing)
        activity?.findViewById<FloatingActionButton>(R.id.fab_edit_memo_done)?.visibility =
            View.GONE
        picAdapter.visible = View.GONE
        picAdapter.notifyDataSetChanged()
        requireActivity().invalidateOptionsMenu()

        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(title.windowToken, 0)
        imm.hideSoftInputFromWindow(content.windowToken, 0)
    }

    override fun onEdit() {
        isEdit = true
        title.isCursorVisible = true
        content.isCursorVisible = true

        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.edit_memo)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab_edit_memo_done)
        fab?.visibility = View.VISIBLE
        fab?.apply {
            setImageResource(R.drawable.ic_done)
            setOnClickListener {
                for (i in 0 until picItem.size) {
                    picItem2.add(picItem.get(i).toString())
                }
                presenter.saveMemo(title.text.toString(), content.text.toString(), picItem2)
            }
        }
        picAdapter.visible = View.VISIBLE
        picAdapter.notifyDataSetChanged()
        requireActivity().invalidateOptionsMenu()
    }


    /**
     * 스낵바 메시지
     */
    override fun showMessage(msg: String) {
        view?.showSnackBar(msg, Snackbar.LENGTH_LONG)
    }

    /**
     * 툴바메뉴 아이템 inflate
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (isShow && !isEdit) {
            inflater.inflate(R.menu.show_menu, menu)
        } else {
            inflater.inflate(R.menu.addedit_menu, menu)
        }
    }

    /**
     * 툴바메뉴 설정
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
            R.id.menu_edit -> onEdit()
            R.id.menu_delete -> presenter.deleteMemo(arguments?.get(ARGUMENT_SHOW_MEMO_ID).toString())
            R.id.menu_attach -> showFilteringPopUpMenu()
        }
        return true
    }

    /**
     * 툴바메뉴 아이템
     */
    override fun showFilteringPopUpMenu() {
        val activity = activity ?: return
        val context = context ?: return
        PopupMenu(context, activity.findViewById(R.id.menu_attach)).apply {
            menuInflater.inflate(R.menu.attach_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.camera -> presenter.callCamera()
                    R.id.gallery -> presenter.callGallery()
                    R.id.url -> presenter.callUrl()
                }
//                presenter.loadTasks(false)
                true
            }
            show()
        }
    }

    /**
     * 갤러리 권한 체크
     */
    override fun showGallery() {
        CheckPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, PICK_GALLERY_ID)
    }

    /**
     * 권한 RESULT
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PICK_GALLERY_ID -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openGallery()
                } else {
                    presenter.showMessage("권한이 거부 되었습니다.")
                }
                return
            }

            PICK_CAMERA_ID -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openCamera()
                } else {
                    presenter.showMessage("권한이 거부 되었습니다.")
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
//        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        intent.type = "image/*"
//        startActivityForResult(Intent.createChooser(intent, "photo"), PICK_GALLERY_ID)

        Intent(context, GalleryActivity::class.java).apply {
            startActivityForResult(this, PICK_GALLERY_ID)
        }

    }


    /**
     * 갤러리 사진 선택 후
     */
    override fun showSuccessGallery(data: Intent?) {
        val pics = data?.getStringArrayListExtra("pics")
        if (pics?.size == 0) {
            presenter.showMessage(getString(R.string.fail_multi_gallery))
        } else {
            for (i in 0 until pics!!.size) {
                picItem.add(pics[i])
            }
            picAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 카메라 권한 체크
     */
    override fun showCamera() {
        CheckPermission(this, Manifest.permission.CAMERA, PICK_CAMERA_ID)
    }

    /**
     * 카메라 실행
     */
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(context!!.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        context!!,
                        "com.example.toy_project",
                        it
                    )
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(intent, PICK_CAMERA_ID)
                }
            }
        }

    }

    /**
     * URL(링크)로 이미지 추가
     */
    @SuppressLint("InflateParams")
    override fun showUrl() {
        val builder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_url, null)
        val inputUrl = dialogView.findViewById<EditText>(R.id.inputUrl)
        val dialog = builder.setView(dialogView)
            .setTitle(R.string.menu_url)
            .setPositiveButton("확인") { dialog, i ->
                val imgUrl = inputUrl.text.toString()
                val urlCheck = URLUtil.isValidUrl(imgUrl)
                if (urlCheck) {
                    picItem.add(imgUrl)
                } else {
                    presenter.showMessage(getString(R.string.fail_url))
                }

                picAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("취소") { dialog, i ->
                dialog.cancel()
            }
            .show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
    }

    override fun failImage() {
        presenter.showMessage(getString(R.string.fail_image))
        picItem.removeAt(picItem.size - 1)
    }


    /**
     * custom file 경로
     */
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    /**
     * 카메라 사진 추가
     */
    override fun showSuccessCamera() {
        picItem.add(photoURI.toString())
        picAdapter.notifyDataSetChanged()
    }

    /**
     * 사진 삭제
     */
    override fun itemRemove(position: Int) {
        picItem.removeAt(position)
        picAdapter.notifyDataSetChanged()
    }

    /**
     * 아무것도 안 적었을시
     */
    override fun showEmptyMemo() {
        presenter.showMessage(getString(R.string.empty_memo))
    }

    /**
     * 저장 했을 때
     * Activity finish
     */
    override fun showMemosList() {
        activity?.apply {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    /**
     * 삭제 되었을 때
     * Activity finish
     */
    override fun showMemosDeleted() {
        activity?.finish()
        presenter.showMessage(getString(R.string.remove_memo))
    }

    /**
     * 이미지 클릭시
     */
    override fun fullImage(image: String) {
        val intent = Intent(this.context, FullScreenImgActivity::class.java)
        intent.putExtra("uri", image)
        startActivity(intent)
    }

    /**
     * focus가 주어지면 edit으로
     */
    private fun focusChange() {
        title.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && content.isFocusable) {
                onEdit()
            }
        }
        content.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && title.isFocusable) {
                onEdit()
            }
        }

        title.setOnClickListener { onEdit() }
        content.setOnClickListener { onEdit() }
    }

}
