package com.swaps273.expense

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.swaps273.expense.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class DebtFragment2 : Fragment() {

    lateinit var debadapt: ArrayAdapter<String>
    lateinit var listdeb:MutableList<String>
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(DebtFragment2.ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.mem_fragment_item_list, container, false)

        // Set the adapter
        var grp: ExpGroup= mainData.data[id1]
        listdeb= mutableListOf<String>()
        for(i in 0..(mainData.data[id1].debt.size-1)){
            for(j in 0..(mainData.data[id1].debt[i].size-1)){
                if(mainData.data[id1].debt[i][j]<=-0.01){
                    listdeb.add(mainData.data[id1].members[j].name+" -> "+mainData.data[id1].members[i].name+": "+(-mainData.data[id1].debt[i][j]).toString())
                }
            }
        }
        var listvdeb: ListView =view.findViewById(R.id.list)
        debadapt= ArrayAdapter<String>(activity!!.applicationContext, android.R.layout.simple_list_item_1, listdeb)
        listvdeb.adapter=debadapt
        listvdeb.emptyView=view.findViewById<TextView>(R.id.textView6)
        view.findViewById<TextView>(R.id.textView6).text="Wuhoo!! No debts!!!"
        return view
    }
    fun refresh() {
        var grp: ExpGroup= mainData.data[id1]
        listdeb.clear()
        for(i in 0..(mainData.data[id1].debt.size-1)){
            for(j in 0..(mainData.data[id1].debt[i].size-1)){
                if(mainData.data[id1].debt[i][j]<=-0.01){
                    listdeb.add(mainData.data[id1].members[j].name+" -> "+mainData.data[id1].members[i].name+": "+(-mainData.data[id1].debt[i][j]).toString())
                }
            }
        }
        debadapt.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            DebtFragment2().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}