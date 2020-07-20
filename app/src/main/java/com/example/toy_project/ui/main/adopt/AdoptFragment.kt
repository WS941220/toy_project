package com.example.toy_project.ui.main.adopt

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.ui.addeditmemo.AddEditMemoActivity
import com.example.toy_project.ui.addeditmemo.AddEditMemoFragment
import com.example.toy_project.ui.memo.MemoAdapter
import com.example.toy_project.ui.stray.StrayActivity
import com.jakewharton.rxbinding2.view.visibility
import dagger.android.support.DaggerFragment
import org.w3c.dom.Text
import javax.inject.Inject

@ActivityScoped
class AdoptFragment : DaggerFragment(),
    AdoptContract.AdoptView, AdoptAdapterR.ItemListener {

    companion object {
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

    private lateinit var adoptAdapterR: AdoptAdapterR
    private val categoryItems: MutableList<AdoptAdapterR.Companion.Item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe()
        presenter.attach(this)


        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Header, "Fruits", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Child, "Apple", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Child, "Orange", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Child, "Banana", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Header, "Cars", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Child, "Audi", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Child, "Aston Martin", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Child, "BMW", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Child, "Cadillac", true))
        categoryItems.add(AdoptAdapterR.Companion.Item(AdoptAdapterR.Header, "유기동물 가족만들기", false))

        val places: AdoptAdapterR.Companion.Item =
            AdoptAdapterR.Companion.Item(AdoptAdapterR.Header, "Places",true)
        places.invisibleChildren.add(
            AdoptAdapterR.Companion.Item(
                AdoptAdapterR.Child,
                "Karnataka", true
            )
        )
        categoryItems.add(places)
        adoptAdapterR = AdoptAdapterR(context, categoryItems, this)

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
        categoryR.adapter = adoptAdapterR

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

    override fun onStartStray() {
        Intent(context, StrayActivity::class.java).apply {
            startActivity(this)
        }
    }

}