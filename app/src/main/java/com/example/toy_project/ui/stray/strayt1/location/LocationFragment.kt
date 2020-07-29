package com.example.toy_project.ui.stray.strayt1.location

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.Item
import com.example.toy_project.ui.stray.strayt1.Strayt1Fragment
import com.example.toy_project.util.SettingPreference
import com.example.toy_project.util.progressOff
import com.example.toy_project.util.progressOn
import com.jakewharton.rxbinding2.view.selected
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_stray_location.*
import kotlinx.android.synthetic.main.item_gallery.view.*
import javax.inject.Inject

@ActivityScoped
class LocationFragment : DaggerFragment(),
    LocationContract.LocationView {

    companion object {
        fun newInstance(): LocationFragment {
            return LocationFragment()
        }
    }

    @Inject
    lateinit var presenter: LocationContract.LocationPresenter
    private lateinit var rootView: View
    private var uprList: MutableList<String> = arrayListOf("전체")
    private var orgList: MutableList<String> = arrayListOf("전체")
    private var careList: MutableList<String> = arrayListOf("전체")

    private var listMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    private lateinit var uprAdapter: ArrayAdapter<String>
    private lateinit var orgAdapter: ArrayAdapter<String>
    private lateinit var careAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.subscribe()
        presenter.attach(this)
        if (Strayt1Fragment.locationState == null) {
            presenter.getUpr()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listMap["uprList"] = arrayListOf("")
        listMap["orgList"] = arrayListOf("")
        listMap["careList"] = arrayListOf("")

        uprSpinner.apply {
            inputType = InputType.TYPE_NULL
            setAdapter(uprAdapter)
            setText("전체", false)
            setOnItemClickListener { parent, view, position, id ->
                Strayt1Fragment.locationState?.putString("seUpr", this.text.toString())
                when (position) {
                    0 -> {
                        orgList.apply {
                            clear()
                            add("전체")
                            orgSpinner.setText("전체", false)
                        }
                        careList.apply {
                            clear()
                            add("전체")
                            careSpinner.setText("전체", false)
                        }
                        presenter.setLocation(
                            "", "", ""
                        )
                    }
                    else -> {
                        presenter.getOrg(listMap["uprList"]?.get(position)!!)
                    }
                }
            }
        }
        orgSpinner.apply {
            inputType = InputType.TYPE_NULL
            setAdapter(orgAdapter)
            setText("전체", false)
            setOnItemClickListener { parent, view, position, id ->
                Strayt1Fragment.locationState?.putString("seOrg", this.text.toString())
                when (position) {
                    0 -> {
                        careList.apply {
                            clear()
                            add("전체")
                            careSpinner.setText("전체", false)
                        }
                        presenter.setLocation(
                            listMap["uprList"]?.get(uprList.indexOf(uprSpinner.text.toString()))!!,
                            "",
                            ""
                        )
                    }
                    else -> {
                        presenter.getCare(
                            listMap["uprList"]?.get(uprList.indexOf(uprSpinner.text.toString()))!!,
                            listMap["orgList"]?.get(position)!!
                        )
                    }
                }
            }
        }
        careSpinner.apply {
            inputType = InputType.TYPE_NULL
            setAdapter(careAdapter)
            setText("전체", false)
            setOnItemClickListener { parent, view, position, id ->
                Strayt1Fragment.locationState?.putString("seCare", this.text.toString())
                presenter.setLocation(
                    listMap["uprList"]?.get(uprList.indexOf(uprSpinner.text.toString()))!!,
                    listMap["orgList"]?.get(orgList.indexOf(orgSpinner.text.toString()))!!,
                    listMap["careList"]?.get(position)!!
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unsubscribe()
        Strayt1Fragment.locationState = Bundle()
        Strayt1Fragment.locationState?.putStringArrayList("upr", ArrayList(uprList))
        Strayt1Fragment.locationState?.putStringArrayList("org", ArrayList(orgList))
        Strayt1Fragment.locationState?.putStringArrayList("care", ArrayList(careList))

        Strayt1Fragment.locationState?.putStringArrayList("sUpr", ArrayList(listMap["uprList"]!!))
        Strayt1Fragment.locationState?.putStringArrayList("sOrg", ArrayList(listMap["orgList"]!!))
        Strayt1Fragment.locationState?.putStringArrayList("sCare", ArrayList(listMap["careList"]!!))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_stray_location, container, false)

        uprAdapter = ArrayAdapter(context!!, R.layout.item_location, uprList)
        orgAdapter = ArrayAdapter(context!!, R.layout.item_location, orgList)
        careAdapter = ArrayAdapter(context!!, R.layout.item_location, careList)

        return rootView
    }

    override fun showUprList(uprList: List<Item>) {
        this.uprList.apply {
            clear()
            add("전체")
        }
        (uprList.indices).forEach {
            listMap["uprList"]?.add(uprList[it].orgCd!!)
            this.uprList.add(uprList[it].orgdownNm!!)
        }
        uprAdapter.notifyDataSetChanged()
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (Strayt1Fragment.locationState != null) {
            uprList.apply {
                clear()
                addAll(Strayt1Fragment.locationState?.getStringArrayList("upr")!!)
            }
            orgList.apply {
                clear()
                addAll(Strayt1Fragment.locationState?.getStringArrayList("org")!!)
            }
            careList.apply {
                clear()
                addAll(Strayt1Fragment.locationState?.getStringArrayList("care")!!)
            }
            listMap["uprList"]?.apply {
                clear()
                addAll(Strayt1Fragment.locationState?.getStringArrayList("sUpr")!!)
            }
            listMap["orgList"]?.apply {
                clear()
                addAll(Strayt1Fragment.locationState?.getStringArrayList("sOrg")!!)
            }
            listMap["careList"]?.apply {
                clear()
                addAll(Strayt1Fragment.locationState?.getStringArrayList("sCare")!!)
            }
            uprAdapter.notifyDataSetChanged()
            orgAdapter.notifyDataSetChanged()
            careAdapter.notifyDataSetChanged()

            uprSpinner.setText(Strayt1Fragment.locationState?.getString("seUpr"), false)
            orgSpinner.setText(Strayt1Fragment.locationState?.getString("seOrg"), false)
            careSpinner.setText(Strayt1Fragment.locationState?.getString("seCare"), false)
        }
    }

    override fun showOrgList(orgList: List<Item>) {
        this.orgList.apply {
            clear()
            add("전체")
        }
        (orgList.indices).forEach {
            listMap["orgList"]?.add(orgList[it].orgCd!!)
            this.orgList.add(orgList[it].orgdownNm!!)
        }
        orgAdapter.notifyDataSetChanged()
    }

    override fun showCareList(careList: List<Item>) {
        this.careList.apply {
            clear()
            add("전체")
        }
        (careList.indices).forEach {
            listMap["careList"]?.add(careList[it].careRegNo!!)
            this.careList.add(careList[it].careNm!!)
        }
        careAdapter.notifyDataSetChanged()
    }

    override fun showProgress(msg: String) {
        activity?.progressOn(msg)
    }

    override fun closeProgress() {
        activity?.progressOff()
    }

}