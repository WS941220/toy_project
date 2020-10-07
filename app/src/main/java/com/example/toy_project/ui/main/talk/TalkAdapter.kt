package com.example.toy_project.ui.main.talk

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.toy_project.R


class TalkAdapter(
    private val context: Context, var items: List<String>, activity: Activity
) : RecyclerView.Adapter<TalkAdapter.ViewHolder>() {

//    private val listener: ClikListener
//
//    init {
//        this.listener = activity as ClikListener
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_talk, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt: TextView = itemView.findViewById(R.id.titleTxt)
        val profile: ImageView = itemView.findViewById(R.id.profile)
        val profileID: TextView = itemView.findViewById(R.id.profileID)
        val dateTxt: TextView = itemView.findViewById(R.id.dateTxt)
        val reportTxt: TextView = itemView.findViewById(R.id.reportTxt)
        val contentTxt: TextView = itemView.findViewById(R.id.contentTxt)
        val likeTxt: TextView = itemView.findViewById(R.id.likeTxt)
        val replyTxt: TextView = itemView.findViewById(R.id.replyTxt)
        val picPager: ViewPager2 = itemView.findViewById(R.id.picPager)
        val likeBtn: Button = itemView.findViewById(R.id.likeBtn)
        val replyBtn: Button = itemView.findViewById(R.id.replyBtn)
        val shareBtn: Button = itemView.findViewById(R.id.shareBtn)
    }

    interface ClikListener {

    }

}