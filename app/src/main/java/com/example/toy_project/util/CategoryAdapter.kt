package com.example.toy_project.util

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.R
import com.example.toy_project.di.model.Category

class CategoryAdapter(
    private val context: Context?,
    private var categories: MutableList<Category>,
    fragment: Fragment?,
    activity: Activity?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val Header: Int = 0
        const val Child: Int = 1
    }

    private lateinit var clickListner: ClickListner

    init {
        if (fragment != null) {
            this.clickListner = fragment as ClickListner
        } else if (activity != null) {
            this.clickListner = activity as ClickListner
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        val dp: Float = context!!.resources.displayMetrics.density
        val subItemPaddingLeft: Float = (18 * dp)
        val subItemPaddingTopAndBottom: Float = (5 * dp)
        when (viewType) {
            Header -> {
                val inflater =
                    parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.item_categoies, parent, false)
                return ListViewHolder(
                    view
                )
            }
            Child -> {
                val itemTextView = TextView(context)
                itemTextView.setPadding(
                    subItemPaddingLeft.toInt(),
                    subItemPaddingTopAndBottom.toInt(),
                    0,
                    subItemPaddingTopAndBottom.toInt()
                )
                itemTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10F)
                itemTextView.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                return object : RecyclerView.ViewHolder(itemTextView) {}
            }
        }
        return ListViewHolder(view!!)
    }

    override fun getItemCount() = categories.size

    override fun getItemViewType(position: Int): Int {
        return categories[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: Category = categories[position]
        when (item.type) {
            Header -> {
                val itemController: ListViewHolder = holder as ListViewHolder
                itemController.refferalItem = item
                itemController.categoryText.text = item.text
                when (item.child) {
                    true -> {
                        if (item.invisibleChildren.size == 0) {
                            itemController.extendableImg.setImageResource(R.drawable.ic_minus)
                        } else {
                            itemController.extendableImg.setImageResource(R.drawable.ic_plus)
                        }
                    }
                    false -> {
                        itemController.extendableImg.visibility = View.GONE
                    }
                }
                itemController.itemView.onThrottleClick {
                    when (item.child) {
                        true -> {
                            if (item.invisibleChildren.size == 0) {
                                item.invisibleChildren = arrayListOf()
                                var count = 0
                                val pos: Int = categories.indexOf(itemController.refferalItem!!)
                                while (categories.size > pos + 1 && categories[pos + 1].type == Child) {
                                    item.invisibleChildren.add(categories.removeAt(pos + 1))
                                    count++
                                }
                                notifyItemRangeRemoved(pos + 1, count)
                                itemController.extendableImg.setImageResource(R.drawable.ic_plus)
                            } else {
                                val pos: Int = categories.indexOf(itemController.refferalItem!!)
                                var index = pos + 1
                                for (i in item.invisibleChildren) {
                                    categories.add(index, i)
                                    index++
                                }
                                notifyItemRangeInserted(pos + 1, index - pos - 1)
                                itemController.extendableImg.setImageResource(R.drawable.ic_minus)
                                item.invisibleChildren = arrayListOf()
                            }
                        }
                        false -> {
                            val uiClass = Class.forName("com.example.toy_project.${item.className}")
                            clickListner.onCategoryClick(
                                holder.categoryText.text.toString(),
                                uiClass,
                                item.isClass
                            )
                        }
                    }
                }
            }
            Child -> {
                val itemTextView = holder.itemView as TextView
                itemTextView.text = categories[position].text
                holder.itemView.onThrottleClick {
                    val uiClass = Class.forName("com.example.toy_project.${item.className}")
                    clickListner.onCategoryClick(itemTextView.text.toString(), uiClass, item.isClass)
                }
            }
        }

    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var refferalItem: Category? = null
        val categoryText: TextView = itemView.findViewById(R.id.categoryText)
        val extendableImg: ImageView = itemView.findViewById(R.id.extendableImg)
    }

    interface ClickListner {
        fun onCategoryClick(title: String, className: Class<*>, isClass: Boolean)
    }


}