package com.swaps273.expense

import android.content.Intent
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
class TransactFragment : Fragment() {

    lateinit var tradapt: ArrayAdapter<String>
    lateinit var listtr:MutableList<String>
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(TransactFragment.ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.mem_fragment_item_list, container, false)

        // Set the adapter
        var grp: ExpGroup= mainData.data[id1]
        listtr= mutableListOf<String>()
        for(i in grp.history){
            listtr.add(grp.members[i.from].name+" paid:"+i.total);
        }
        var listvdeb: ListView =view.findViewById(R.id.list)
        listvdeb.setOnItemClickListener { parent, v1, position, _ ->
            var i = Intent(activity!!.applicationContext,TransactionInfoActivity::class.java)
            i.putExtra("id",id1)
            i.putExtra("prompt",true)
            i.putExtra("trval",position)
            this.startActivity(i)
        }
        tradapt= ArrayAdapter<String>(activity!!.applicationContext, android.R.layout.simple_list_item_1, listtr)
        listvdeb.adapter=tradapt
        listvdeb.emptyView=view.findViewById<TextView>(R.id.textView6)
        view.findViewById<TextView>(R.id.textView6).text="No history yet. Let's make some?"
        return view
    }

    fun refresh() {

        var grp: ExpGroup= mainData.data[id1]
        listtr.clear()
        for(i in grp.history){
            listtr.add(grp.members[i.from].name+" paid:"+i.total);
        }
        tradapt.notifyDataSetChanged()
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
            TransactFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}