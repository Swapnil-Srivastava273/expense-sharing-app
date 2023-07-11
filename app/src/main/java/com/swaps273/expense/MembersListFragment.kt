package com.swaps273.expense

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.swaps273.expense.*


/**
 * A fragment representing a list of Items.
 */

class MembersListFragment : Fragment() {
    lateinit var memadapt:ArrayAdapter<String>
    lateinit var listmem:MutableList<String>
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.mem_fragment_item_list, container, false)

        // Set the adapter
        var grp: ExpGroup= mainData.data[id1]
        listmem= mutableListOf<String>()
        for(i in grp.members){
            listmem.add(i.name)
        }
        var listvmem:ListView=view.findViewById(R.id.list)
        memadapt=ArrayAdapter<String>(activity!!.applicationContext, android.R.layout.simple_list_item_1, listmem)
        listvmem.adapter=memadapt
        listvmem.emptyView=view.findViewById<TextView>(R.id.textView6)
        view.findViewById<TextView>(R.id.textView6).text="No members yet."
        listvmem.setOnItemClickListener { parent, v1, position, _ ->
            var alert=AlertDialog.Builder(activity)
            alert.setTitle(grp.members[position].name)
            alert.setMessage("Email: "+grp.members[position].email)
            alert.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->{}})
            alert.show()
        }
        return view
    }
    fun refresh(){
        var grp: ExpGroup= mainData.data[id1]
        listmem.clear()
        for(i in grp.members){
            listmem.add(i.name)
        }
        memadapt.notifyDataSetChanged()




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
                MembersListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}