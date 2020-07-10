package com.example.toy_project.ui.gallery

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toy_project.R

class GalleryAdapter(
    private val context: Context?, var pics: List<String>, activity: Activity
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private val sPic: MutableList<String> = arrayListOf()
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Glide.with(context!!).load(pics[position]).into(holder.ivImg)
        holder.ivImg.setOnClickListener {
            val pic = pics[position]
            if (holder.vSelected.visibility == View.VISIBLE) sPic.remove(pic) else sPic.add(pic)
            holder.vSelected.visibility = if (holder.vSelected.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    override fun getItemCount(): Int = pics.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(itemView)
    }

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImg: ImageView = itemView.findViewById(R.id.ivImg)
        val vSelected: View = itemView.findViewById(R.id.vSelected)
    }

}