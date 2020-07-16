package com.example.toy_project.ui.gallery

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.util.setupActionBar
import com.example.toy_project.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_gallery.*
import javax.inject.Inject

class GalleryActivity : DaggerAppCompatActivity(), GalleryContract.View {

    @Inject
    lateinit var presenter: GalleryContract.Presenter

    private val picItem: MutableList<String> = arrayListOf()
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        galleryAdapter = GalleryAdapter(baseContext, picItem, this)
        galleryRecycler.layoutManager = GridLayoutManager(baseContext, 3) as RecyclerView.LayoutManager
        galleryRecycler.adapter = galleryAdapter

        /**
         * 툴바
         */
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true);
            setDisplayShowHomeEnabled(true);
            setTitle(R.string.gallery_name)
        }
        getGallery()

    }

    /**
     * 메뉴 inflate
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)
        return true;
    }

    /**
     * 툴바메뉴 설정
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> this.onBackPressed()
            R.id.menu_success -> sendPictures()
        }
        return true
    }

    private fun sendPictures() {
        val intent = Intent()
        intent.putStringArrayListExtra("pics", galleryAdapter.sPic)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getGallery() {
        val cursor = getGalleryCursor(baseContext)
        if (cursor!!.moveToFirst()) do {
            val index = cursor.getColumnIndex(MediaStore.MediaColumns._ID)
            picItem.add(getImageUri(cursor.getString(index)).toString())
        } while (cursor.moveToNext())
        cursor.close()
        galleryAdapter.notifyDataSetChanged()
    }

    private fun getGalleryCursor(context: Context?): Cursor? {
        val externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val columns = arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.DATE_MODIFIED)
        val orderBy = MediaStore.MediaColumns.DATE_MODIFIED //order data by modified
        return context!!.contentResolver
            .query(
                externalUri,
                columns,
                null,
                null,
                "$orderBy DESC"
            )
    }

    private fun getImageUri(path: String) = ContentUris.withAppendedId(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        path.toLong()
    )


}