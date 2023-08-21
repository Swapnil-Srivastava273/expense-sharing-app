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
import java.lang.Double.min
import java.util.*
import kotlin.math.abs

/**
 * A fragment representing a list of Items.
 */
data class Pair2( var first : Double, var second : Int) : Comparable<Pair2>{
    override fun compareTo(other: Pair2): Int {
        if(this.first == other.first && this.second == other.second) return 0;
        if(this.first > other.first) return 1;
        if(this.first < other.first) return -1;
        if(this.second > other.second) return 1;
        return -1;
    }
};
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
        var diff = mutableListOf<Double>();
        for(i in 0 .. (mainData.data[id1].debt.size) -1){
            diff.add(0.0);
        }
        for(i in 0..(mainData.data[id1].debt.size-1)) {
            for (j in 0..(mainData.data[id1].debt[i].size - 1)) {
                diff[i] -= mainData.data[id1].debt[i][j];
                diff[j] += mainData.data[id1].debt[i][j];
            }
        }
        var values: SortedSet<Pair2> = TreeSet();
        for(i in 0 .. (mainData.data[id1].debt.size -1)){
            values.add(Pair2(diff[i]/2, i));
        }
        var eps: Double = 0.0000001;
        while(values.size > 0){
            var u = values.first();
            var v = values.last();
            values.remove(u);
            values.remove(v);
            val cost = min(-u.first, v.first);
            if(cost < eps) continue;
            listdeb.add(mainData.data[id1].members[u.second].name+" -> "+mainData.data[id1].members[v.second].name+": "+(cost).toString());
            u = Pair2(u.first + cost, u.second);
            v = Pair2(v.first - cost, v.second);
            if(abs(u.first) > eps)values.add(u);
            if(abs(v.first) > eps)values.add(v);
        }
//        for(i in 0..(mainData.data[id1].debt.size-1)){
//            for(j in 0..(mainData.data[id1].debt[i].size-1)){
//                if(mainData.data[id1].debt[i][j]<=-0.01){
//                    listdeb.add(mainData.data[id1].members[j].name+" -> "+mainData.data[id1].members[i].name+": "+(-mainData.data[id1].debt[i][j]).toString())
//                }
//            }
//        }
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
        var diff = mutableListOf<Double>();
        for(i in 0 .. (mainData.data[id1].debt.size) -1){
            diff.add(0.0);
        }
        for(i in 0..(mainData.data[id1].debt.size-1)) {
            for (j in 0..(mainData.data[id1].debt[i].size - 1)) {
                diff[i] -= mainData.data[id1].debt[i][j];
                diff[j] += mainData.data[id1].debt[i][j];
            }
        }
        var values: SortedSet<Pair2> = TreeSet();
        for(i in 0 .. (mainData.data[id1].debt.size -1)){
            values.add(Pair2(diff[i]/2, i));
        }
        var eps: Double = 0.0000001;
        while(values.size > 0){
            var u = values.first();
            var v = values.last();
            values.remove(u);
            values.remove(v);
            val cost = min(-u.first, v.first);
            if(cost < eps) continue;
            listdeb.add(mainData.data[id1].members[u.second].name+" -> "+mainData.data[id1].members[v.second].name+": "+(cost).toString());
            u = Pair2(u.first + cost, u.second);
            v = Pair2(v.first - cost, v.second);
            if(abs(u.first) > eps)values.add(u);
            if(abs(v.first) > eps)values.add(v);
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