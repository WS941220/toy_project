package com.example.toy_project.ui.memo

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.toy_project.R
import com.example.toy_project.di.model.Memo

class MemoAdapter(
    private val context: Context?, var memos: List<Memo>, var visible: Int, var check: Boolean,
    fragment: Fragment
) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    private val listener: MemoItemListener

    init {
        this.listener = fragment as MemoItemListener
    }


    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {

        val title = memos[position].title
        val content = memos[position].content
        val image = Uri.parse(memos[position].image[0])

        holder.title.text = title
        holder.content.text = content
        if(image != null) {
            Glide.with(this.context!!)
                .load(image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(holder.pic)
        }

        holder.check.visibility = visible
        holder.check.isChecked = memos[position].isChecked

        if(visible == View.VISIBLE) {
            holder.check.isChecked = check
            if(check) {
                listener.onCheckAllMemos()
            } else if (!check) {
                listener.onCancelAllMemos()
            }
            holder.mainCard.setOnClickListener {
                holder.check.isChecked = !holder.check.isChecked
            }
            holder.check.setOnCheckedChangeListener { _ , isChecked ->
                if(isChecked) {
                    listener.onCheckMemoClick(memos[position])
                } else {
                    listener.onCancelMemoClick(memos[position])
                }
            }
        } else {
            holder.mainCard.setOnClickListener {
                listener.onMemoClick(memos[position])
            }
        }

    }

    override fun getItemCount(): Int = memos.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemoViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_memo, parent, false)
        return MemoViewHolder(itemView)
    }



    class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainCard: CardView = itemView.findViewById(R.id.mainCard)
        val title: TextView = itemView.findViewById(R.id.title)
        val content: TextView = itemView.findViewById(R.id.content)
        val pic: ImageView = itemView.findViewById(R.id.pic)
        val check: CheckBox = itemView.findViewById(R.id.check)

    }

    interface MemoItemListener {
        fun onCheckAllMemos()

        fun onCancelAllMemos()

        fun onCheckMemoClick(checkMemo: Memo)

        fun onCancelMemoClick(cancelMemo: Memo)

        fun onMemoClick(clickMemo: Memo)
    }

}