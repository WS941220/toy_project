package com.example.toy_project.ui.main.adopt

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.R
import com.example.toy_project.di.model.Memo
import com.example.toy_project.ui.memo.MemoAdapter

class AdoptAdapterR(
    private val context: Context?, var categories: MutableList<String>, fragment: Fragment
) : RecyclerView.Adapter<AdoptAdapterR.ViewHolder>()  {

    private val listener: ItemListener

    init {
        this.listener = fragment as ItemListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_categoies, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryText = categories[position]
        holder.categoryText.text = categoryText

        holder.mainCard.setOnClickListener {
            when(holder.categoryText.text) {
                "TEST1" -> { listener.onStartStray()}
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainCard: CardView = itemView.findViewById(R.id.mainCard)
        val categoryText: TextView = itemView.findViewById(R.id.categoryText)
    }

    interface ItemListener {
        fun onStartStray()
    }
}