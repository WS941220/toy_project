package com.example.toy_project.ui.stray.strayt1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toy_project.R
import com.example.toy_project.di.Scoped.ActivityScoped
import com.example.toy_project.di.model.Item
import dagger.android.support.DaggerFragment
import javax.inject.Inject


@ActivityScoped
class Strayt1Fragment : DaggerFragment(),
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

    private var strayList: MutableList<Item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        strayAdapter = Strayt1Adapter(context, strayList, this)
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
        presenter.getStrayList(context!!, activity!!)
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
        rootView = inflater.inflate(R.layout.fragment_stray_t1, container, false)
        with(rootView) {
            strayRecyler = findViewById(R.id.strayRecyler)
        }

        val mLayoutManager = LinearLayoutManager(context)
        strayRecyler.layoutManager = mLayoutManager
        strayRecyler.adapter = strayAdapter

        return rootView
    }

    override fun showStrayList(strayList: List<Item>) {
        this.strayList.addAll(strayList)
        strayAdapter.notifyDataSetChanged()
    }

}