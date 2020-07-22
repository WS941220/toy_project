package com.example.toy_project.ui.stray.strayt1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.Item
import com.example.toy_project.ui.stray.strayt1.calendar.CalendarFragment
import com.example.toy_project.ui.stray.strayt1.location.LocationFragment
import com.example.toy_project.util.addFragmentToActivity
import com.example.toy_project.util.addFragmentToFragment
import com.example.toy_project.util.replaceFragmentInActivity
import com.example.toy_project.util.replaceFragmentInFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.reflect.cast


@ActivityScoped
class Strayt1Fragment(
    private val navLabels: Array<Int> = arrayOf(
        R.string.tab_home,
        R.string.tab_adopt
    ),
    private val navFragments: Array<Any> = arrayOf(
        LocationFragment,
        CalendarFragment
    ),
    private val navIcons: Array<Int> = arrayOf(
        R.drawable.ic_drop_up,
        R.drawable.ic_drop_up
    ),
    private val navIconsActive: Array<Int> = arrayOf(
        R.drawable.ic_drop_down,
        R.drawable.ic_drop_down
    )
) : DaggerFragment(),
    Strayt1Contract.Strayt1View {

    companion object {
        fun newInstance(): Strayt1Fragment {
            return Strayt1Fragment()
        }
    }

    @Inject
    lateinit var presenter: Strayt1Contract.Strayt1Presenter
    private lateinit var rootView: View
    private lateinit var strayRecyler: RecyclerView
    private lateinit var strayAdapter: Strayt1Adapter
    private lateinit var tabs: TabLayout
    private lateinit var frameLayout: FrameLayout

    private var strayList: MutableList<Item> = arrayListOf()
    private var callStray: MutableMap<String, String> = hashMapOf()
    private var numPager: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attach(this)
        strayAdapter = Strayt1Adapter(context, strayList, this)
        callStray["num"] = "$numPager"
        presenter.getStrayList(context!!, activity!!, callStray)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
    }

    @ExperimentalStdlibApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        strayRecyler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = LinearLayoutManager::class.cast(recyclerView.layoutManager)
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                if (lastVisible >= totalItemCount - 1) {
                    callStray["num"] = "${numPager + 1}"
                    presenter.getStrayList(context!!, activity!!, callStray)
                }
            }
        })

        (0..1).forEach {
            tabs.addTab(tabs.newTab().setText(""))
        }

        (0 until tabs.tabCount).forEach {
            tabs.getTabAt(it)?.customView = changeTab(it, null, false)

        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView =
                    changeTab(tab!!.position, tab.customView as LinearLayout, true)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (frameLayout.visibility) {
                    View.VISIBLE -> {
                        tab?.customView =
                            changeTab(tab!!.position, tab.customView as LinearLayout, false)
                    }
                    View.GONE -> {
                        tab?.customView =
                            changeTab(tab!!.position, tab.customView as LinearLayout, true)
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView =
                    changeTab(tab!!.position, tab.customView as LinearLayout, false)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_stray_t1, container, false)
        with(rootView) {
            strayRecyler = findViewById(R.id.strayRecyler)
            tabs = findViewById(R.id.tabs)
            frameLayout = findViewById(R.id.container)
        }
        childFragmentManager.findFragmentById(R.id.container) as LocationFragment?
            ?: LocationFragment.newInstance().also {
                addFragmentToFragment(it, "STRAY")
            }

        val mLayoutManager = LinearLayoutManager(context)
        strayRecyler.layoutManager = mLayoutManager
        strayRecyler.adapter = strayAdapter

        return rootView
    }

    @SuppressLint("InflateParams")
    private fun changeTab(
        it: Int,
        tab: LinearLayout?,
        check: Boolean
    ): LinearLayout {
        val customTab: LinearLayout =
            tab ?: LayoutInflater.from(context).inflate(R.layout.tab_stray_t1, null) as LinearLayout
        val tab_label = customTab.findViewById(R.id.nav_label) as TextView
        val tab_icon = customTab.findViewById(R.id.nav_icon) as ImageView
        tab_label.text = resources.getString(navLabels[it])

        when (check) {
            true -> {
                navFragments[it].also { fragment ->
                    when (fragment) {
                        LocationFragment -> LocationFragment.newInstance().also {
                            replaceFragmentInFragment(
                                it,
                                R.id.container
                            )
                        }
                        CalendarFragment -> CalendarFragment.newInstance().also {
                            replaceFragmentInFragment(
                                it,
                                R.id.container
                            )
                        }

                    }
                }
                frameLayout.visibility = View.VISIBLE
                tab_label.setTextColor(ContextCompat.getColor(context!!, R.color.primary))
                tab_icon.setImageResource(navIconsActive[it])
            }
            false -> {
                frameLayout.visibility = View.GONE
                tab_label.setTextColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.colorPrimaryDark
                    )
                )
                tab_icon.setImageResource(navIcons[it])
            }
        }
        return customTab
    }

    private fun TabLayout.setTabWidthAsWrapContent(tabPosition: Int, weight: Float, width: Int) {
        val layout = (this.getChildAt(0) as LinearLayout).getChildAt(tabPosition) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = weight
        layoutParams.width = width
        layoutParams.gravity = Gravity.START
        layout.layoutParams = layoutParams
    }


    override fun showStrayList(strayList: List<Item>) {
        this.strayList.addAll(strayList)
        strayAdapter.notifyDataSetChanged()
    }

}