package com.example.toy_project.ui.stray.strayt1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.Item
import com.example.toy_project.ui.stray.strayt1.calendar.CalendarFragment
import com.example.toy_project.ui.stray.strayt1.location.LocationFragment
import com.example.toy_project.ui.stray_detail.Stray_DetailActivity
import com.example.toy_project.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_stray_t1.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.reflect.cast


@ActivityScoped
class Strayt1Fragment(
    private val navLabels: Array<Int> = arrayOf(
        R.string.stray_location,
        R.string.stray_date
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
    Strayt1Contract.Strayt1View, Strayt1Adapter.ClickListener {

    companion object {
        var locationState: Bundle? = null
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
    private lateinit var bottomSheet: LinearLayout
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>

    private var strayList: MutableList<Item> = arrayListOf()
    private var numPager: Int = 1

    @SuppressLint("SimpleDateFormat")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val cal: Calendar = Calendar.getInstance().apply {
            add(Calendar.DATE, -15)
        }
        presenter.setDefault(
            SimpleDateFormat("yyyyMMdd").format(
                Date(cal.timeInMillis)
            ), SimpleDateFormat("yyyyMMdd").format(
                Date()
            ), "", "", ""
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attach(this)
        strayAdapter = Strayt1Adapter(context, strayList, this)
        presenter.getStrayList(numPager)
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
                    numPager++
                    presenter.getStrayList(numPager)
                }
            }
        })

        (0 until navLabels.count()).forEach {
            tabs.addTab(tabs.newTab().setText(resources.getString(navLabels[it])))
        }

        (0 until tabs.tabCount).forEach {
            when (it) {
                0 -> tabs.getTabAt(it)?.customView = changeTab(it, null, true)
                1 -> tabs.getTabAt(it)?.customView = changeTab(it, null, false)
            }
        }

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                changeTab(tab!!.position, tab.customView, true)
                when (tab.position) {
                    0 -> sheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    1 -> sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView =
                    changeTab(tab!!.position, tab.customView, false)
            }
        })

        filterIcon.setOnClickListener { toggleFilters() }
        searchIcon.setOnClickListener { searchLists() }


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
            bottomSheet = findViewById(R.id.contentLayout)
        }

        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.isFitToContents = false
        sheetBehavior.isHideable = false
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        val mLayoutManager = LinearLayoutManager(context)
        strayRecyler.layoutManager = mLayoutManager
        strayRecyler.adapter = strayAdapter

        return rootView
    }

    private fun toggleFilters() {
        if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            tabs.selectTab(tabs.getTabAt(tabs.selectedTabPosition))
            when (tabs.selectedTabPosition) {
                0 -> sheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                1 -> sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            filterIcon.setImageResource(R.drawable.ic_drop_up)
        } else {
            sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            filterIcon.setImageResource(R.drawable.ic_search)
        }
    }

    private fun searchLists() {
        strayList.clear()
        numPager = 1
        presenter.getStrayList(numPager)
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        filterIcon.setImageResource(R.drawable.ic_search)
    }

    @SuppressLint("SetTextI18n")
    override fun setBottomTitle(
        s_date: String,
        e_date: String
    ) {
        bottomTitle.text = "${locationState?.getString("seUpr") ?: "전체"} | " +
                "${locationState?.getString("seOrg") ?: "전체"} | " +
                "${locationState?.getString("seCare") ?: "전체"} | " +
                "${view?.dateFormat(s_date)} ~ ${view?.dateFormat(e_date)}"
    }


    @SuppressLint("InflateParams")
    private fun changeTab(
        it: Int,
        tab: View?,
        check: Boolean
    ): View? {
        val customTab: View =
            tab ?: LayoutInflater.from(context).inflate(R.layout.tab_stray_t1, null)
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
                tab_label.setTextColor(ContextCompat.getColor(context!!, R.color.primary))
                tab_icon.setImageResource(navIconsActive[it])
            }
            false -> {
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


    override fun showStrayList(strayList: List<Item>) {
        this.strayList.addAll(strayList)
        if (this.strayList.isEmpty()) {
            noList.visibility = View.VISIBLE
        } else {
            noList.visibility = View.GONE
        }
        strayAdapter.notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onItemClick(view: View, position: Int) {

        val img: Pair<View, String> =
            Pair.create(view, view.transitionName)
        val optionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, img)

        val intent = Intent(context, Stray_DetailActivity::class.java).apply {
//            val stray: MutableList<String> = arrayListOf()
           putExtra("img", strayList[position].popfile)
        }
        startActivity(intent, optionsCompat.toBundle())
    }

    override fun showProgress(msg: String) {
        activity?.progressOn(msg)
    }

    override fun closeProgress() {
        activity?.progressOff()
    }


}