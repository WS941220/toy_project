package com.example.toy_project.ui.show_list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.R
import com.example.toy_project.di.model.Category
import com.example.toy_project.ui.login.LoginContract
import com.example.toy_project.ui.main.adopt.AdoptFragment
import com.example.toy_project.ui.main.talk.TalkAdapter
import com.example.toy_project.ui.main.talk.TalkFragment
import com.example.toy_project.util.CategoryAdapter
import com.example.toy_project.util.setupActionBar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_show_list.*
import javax.inject.Inject

class ShowListActivity : DaggerAppCompatActivity(), CategoryAdapter.ClickListner {

    @Inject
    lateinit var presenter: ShowListContract.Presenter

    private lateinit var categoryRecycler: RecyclerView
    private lateinit var showRecycler: RecyclerView
    private lateinit var action_category: View
    private lateinit var category_title: TextView
    private lateinit var category_btn: ImageView

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var talkAdapter: TalkAdapter

    private val categoryItems: MutableList<Category> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        val part = intent.getStringExtra("part")
        val title = intent.getStringExtra("title")
        @Suppress("UNCHECKED_CAST")
        val category = intent.getSerializableExtra("category") as MutableList<Category>

        categoryRecycler = findViewById(R.id.categoryRecycler)
        showRecycler = findViewById(R.id.showRecycler)

        setupActionBar(R.id.toolbar) {
            when (part) {
                TalkFragment.ARGUMENT_TALK, AdoptFragment.ARGUMENT_ADOPT -> setCustomView(R.layout.actionbar_category)
            }
            setDisplayShowCustomEnabled(true)
            setTitle(R.string.nothing)
        }

        when (part) {
            TalkFragment.ARGUMENT_TALK, AdoptFragment.ARGUMENT_ADOPT -> {
                categoryAdapter = CategoryAdapter(
                    baseContext,
                    categoryItems,
                    null,
                    this
                )
                action_category =
                    supportActionBar?.customView?.findViewById(
                        R.id.action_category
                    ) as View
                category_title =
                    supportActionBar?.customView?.findViewById(
                        R.id.category_title
                    ) as TextView
                category_btn =
                    supportActionBar?.customView?.findViewById(
                        R.id.category_btn
                    ) as ImageView

                action_category.setOnClickListener {
                    when (categoryRecycler.visibility) {
                        View.GONE -> {
                            category_btn.setImageResource(R.drawable.ic_drop_down)
                            categoryRecycler.visibility = View.VISIBLE
                        }
                        View.VISIBLE -> {
                            category_btn.setImageResource(R.drawable.ic_drop_up)
                            categoryRecycler.visibility = View.GONE

                        }
                    }
                }
                categoryItems.addAll(category)
                category_title.text = title
                val mLayoutManager = LinearLayoutManager(baseContext)
                categoryRecycler.layoutManager = mLayoutManager
                categoryRecycler.adapter = categoryAdapter
            }
        }

        when(part) {
            TalkFragment.ARGUMENT_TALK -> {
                val list = presenter.getTalkList()
                talkAdapter= TalkAdapter(baseContext, list, this)
                val mLayoutManager = LinearLayoutManager(baseContext)
                showRecycler.layoutManager = mLayoutManager
                showRecycler.adapter = talkAdapter
            }
        }


    }

    override fun onCategoryClick(title: String, className: Class<*>, isClass: Boolean) {
        when(isClass) {
            true -> startActivity(Intent(this, className))
            false -> {
                category_title.text = title
                categoryRecycler.visibility = View.GONE
            }
        }
    }
}


