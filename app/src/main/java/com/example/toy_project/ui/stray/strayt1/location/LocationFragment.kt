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
        presenter.getUpr()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uprSpinner.apply {
            inputType = InputType.TYPE_NULL
            setAdapter(uprAdapter)
            setSelection(0)
            setOnItemClickListener { parent, view, position, id ->
                presenter.getOrg(listMap["uprList"]?.get(position)!!)
            }
        }
        orgSpinner.apply {
            inputType = InputType.TYPE_NULL
            setAdapter(orgAdapter)
            setSelection(0)
            setOnItemClickListener { parent, view, position, id ->
                presenter.getCare(
                    listMap["uprList"]?.get(uprList.indexOf(uprSpinner.text.toString()))!!,
                    listMap["orgList"]?.get(position)!!
                )
            }
        }
        careSpinner.apply {
            inputType = InputType.TYPE_NULL
            setAdapter(careAdapter)
            setSelection(0)
            setOnItemClickListener { parent, view, position, id ->

            }
        }
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
        listMap["uprList"] = arrayListOf("")
        (uprList.indices).forEach {
            listMap["uprList"]?.add(uprList[it].orgCd!!)
            this.uprList.add(uprList[it].orgdownNm!!)
        }
        uprAdapter.notifyDataSetChanged()
    }

    override fun showOrgList(orgList: List<Item>) {
        this.orgList.apply {
            clear()
            add("전체")
        }
        listMap["orgList"] = arrayListOf("")
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
        listMap["careList"] = arrayListOf("")
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