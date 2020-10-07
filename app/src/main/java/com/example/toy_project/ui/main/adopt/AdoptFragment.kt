package com.example.toy_project.ui.main.adopt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.Category
import com.example.toy_project.ui.main.talk.TalkFragment

import com.example.toy_project.util.CategoryAdapter
import com.example.toy_project.util.errorDialog
import com.example.toy_project.util.progressOff
import com.example.toy_project.util.progressOn
import dagger.android.support.DaggerFragment
import java.io.Serializable
import javax.inject.Inject

@ActivityScoped
class AdoptFragment : DaggerFragment(),
    AdoptContract.AdoptView, CategoryAdapter.ClickListner {

    companion object {
        const val ARGUMENT_ADOPT = "ADOPT"

        fun newInstance(): AdoptFragment {
            return AdoptFragment()
        }
    }

    @Inject
    lateinit var presenter: AdoptContract.AdoptPresenter
    private lateinit var rootView: View
    private lateinit var categories: View
    private lateinit var categoryR: RecyclerView
    private lateinit var action_category: View
    private lateinit var category_title: TextView
    private lateinit var category_btn: ImageView

    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryItems: MutableList<Category> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe()
        presenter.attach(this)
        presenter.getCategories()
        categoryAdapter = CategoryAdapter(
            context,
            categoryItems,
            this,
            null
        )

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
        rootView = inflater.inflate(R.layout.fragment_main_adopt, container, false)

        with(rootView) {
            categoryR = findViewById(R.id.categoriesR)
            categories = findViewById(R.id.categories)
            requireActivity().invalidateOptionsMenu()
            val actionbar = (activity as AppCompatActivity).supportActionBar
            actionbar?.apply {
                setCustomView(R.layout.actionbar_category)
                setDisplayShowCustomEnabled(true)
                setTitle(R.string.nothing)
            }
            action_category =
                (activity as AppCompatActivity).supportActionBar?.customView?.findViewById(
                    R.id.action_category
                ) as View
            category_title =
                (activity as AppCompatActivity).supportActionBar?.customView?.findViewById(
                    R.id.category_title
                ) as TextView
            category_btn =
                (activity as AppCompatActivity).supportActionBar?.customView?.findViewById(
                    R.id.category_btn
                ) as ImageView

            action_category.setOnClickListener {
                when (categories.visibility) {
                    View.GONE -> {
                        category_btn.setImageResource(R.drawable.ic_drop_down)
                        categories.visibility = View.VISIBLE
                    }
                    View.VISIBLE -> {
                        category_btn.setImageResource(R.drawable.ic_drop_up)
                        categories.visibility = View.GONE
                    }
                }
            }
        }
        val mLayoutManager = LinearLayoutManager(context)
        categoryR.layoutManager = mLayoutManager
        categoryR.adapter = categoryAdapter

        setHasOptionsMenu(true)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    /**
     * 툴바 메뉴 선택
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
            }
        }
        return true
    }

    override fun onCategoryClick(title: String, className: Class<*>, isClass: Boolean) {
        val intent = Intent(activity, className).apply {
            putExtra("part", ARGUMENT_ADOPT)
            putExtra("title", title)
            putExtra("category", categoryItems as Serializable)
        }
        startActivity(intent)
    }

    override fun showCategories(categoryItems: MutableList<Category>) {
        this.categoryItems.addAll(categoryItems)
    }

    override fun errorDialog(message: String) {
        activity?.errorDialog(message)
    }

    override fun showProgress(msg: String) {
        activity?.progressOn(msg)
    }

    override fun closeProgress() {
        activity?.progressOff()
    }

}