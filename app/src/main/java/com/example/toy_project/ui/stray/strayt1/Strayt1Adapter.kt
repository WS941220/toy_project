package com.example.toy_project.ui.stray.strayt1

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toy_project.R
import com.example.toy_project.di.model.Item
import com.example.toy_project.util.Format
import java.text.SimpleDateFormat

class Strayt1Adapter(
    private val context: Context?, var strayList: MutableList<Item>, fragment: Fragment
) : RecyclerView.Adapter<Strayt1Adapter.Strayt1ViewHolder>() {

    private val listener: ClickListener

    init {
        this.listener = fragment as ClickListener
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Strayt1ViewHolder, position: Int) {
        val stray = strayList[position]
        val sSexCd = when(stray.sexCd) {
            "M" -> "수컷"
            "F" -> "암컷"
            else -> "미상"
        }
        Glide.with(context!!).load(stray.popfile).into(holder.picImg)
        holder.stateTxt.text = stray.processState
        holder.dateTxt.text = "${Format.dateFormat(stray.noticeSdt!!)} ~ ${Format.dateFormat(stray.noticeEdt!!)}"
        holder.kindTxt.text = "${stray.kindCd} | ${stray.colorCd} | $sSexCd".replace("[기타축종] ", "")
        holder.noticeTxt.text = stray.noticeNo
        holder.placeTxt.text = stray.happenPlace

        when {
            stray.processState!!.contains("보호중") -> {
                holder.stateTxt.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            stray.processState!!.contains("종료") -> {
                holder.stateTxt.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            else -> {
                holder.stateTxt.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }
        }

        holder.itemView.setOnClickListener { listener.onItemClick(holder.picImg, position) }

    }

    override fun getItemCount(): Int = strayList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Strayt1ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_strayt1, parent, false)

        return Strayt1ViewHolder(itemView)
    }

    class Strayt1ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stateTxt: TextView = itemView.findViewById(R.id.stateTxt)
        val dateTxt: TextView = itemView.findViewById(R.id.dateTxt)
        val kindTxt: TextView = itemView.findViewById(R.id.kindTxt)
        val noticeTxt: TextView = itemView.findViewById(R.id.noticeTxt)
        val placeTxt: TextView = itemView.findViewById(R.id.placeTxt)
        val picImg: ImageView = itemView.findViewById(R.id.picImg)
    }

    interface ClickListener {
        fun onItemClick(view: View, position: Int)
    }


}