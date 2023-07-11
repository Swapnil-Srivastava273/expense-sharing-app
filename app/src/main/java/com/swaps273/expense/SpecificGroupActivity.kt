package com.swaps273.expense

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.swaps273.expense.databinding.ActivitySpecificGroupBinding
import com.swaps273.expense.*
import com.swaps273.expense.databinding.ActivityMainBinding
import com.swaps273.expense.ui.main.PlaceholderFragment
var id1=0
var tabs:TabLayout?=null
var viewPager:ViewPager2?=null
lateinit var ctxgrp:SpecificGroupActivity
lateinit var sectionsPagerAdapter:TabsPagerAdapter
class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {
    lateinit var memfrag:MembersListFragment
    lateinit var debFrag:DebtFragment2
    lateinit var trFrag:TransactFragment
    var created=false;
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // # Music Fragment
                val bundle = Bundle()
                bundle.putInt("id",id1)
                //bundle.putString("fragmentName", "Music Fragment")
                val debFragment = DebtFragment2.newInstance(1)
                debFragment.setArguments(bundle)
                //musicFragment.arguments = bundle
                debFrag=debFragment
                return debFragment
            }
            1 -> {
                // # Movies Fragment
                val bundle = Bundle()
                bundle.putInt("id",id1)
                val fragobj = MembersListFragment.newInstance(1)
                fragobj.setArguments(bundle)
                memfrag=fragobj
                created=true
                return fragobj

            }
            2 -> {
                // # Books Fragment
                val bundle = Bundle()
                bundle.putInt("id",id1)
                //bundle.putString("fragmentName", "Music Fragment")
                val trFragment = TransactFragment.newInstance(1)
                trFragment.setArguments(bundle)
                //musicFragment.arguments = bundle
                trFrag=trFragment
                return trFragment
            }
            else -> return PlaceholderFragment.newInstance(0)
        }

    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}
class SpecificGroupActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySpecificGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctxgrp=this
        id1=intent.getIntExtra("id",-1)
        mainData= readData(filesDir)
        binding = ActivitySpecificGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
        tabs = findViewById<TabLayout>(R.id.tabs)
        viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val titlebar:TextView=findViewById(R.id.title)
        titlebar.text= mainData.data[id1].name
        sectionsPagerAdapter = TabsPagerAdapter(supportFragmentManager,lifecycle,3);
        viewPager!!.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs!!, viewPager!!) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Debts 💵"
                    //tab.setIcon(R.drawable.ic_music)
                }
                1 -> {
                    tab.text = "Members 👤"

                    //tab.setIcon(R.drawable.ic_movie)

                }
                2 -> {
                    tab.text = "History ⌚"
                    //tab.setIcon(R.drawable.ic_book)
                }

            }
            // Change color of the icons
//            tab.icon?.colorFilter =
//                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
//                    Color.WHITE,
//                    BlendModeCompat.SRC_ATOP
//                )
        }.attach()
        binding.fab.setOnClickListener {
//            when(viewPager!!.currentItem){
//                0->{
//                    Snackbar.make(it, "Nah Nah", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show()
                    val bundle = Bundle()
                    bundle.putInt("id",id1)
                    bundle.putBoolean("prompt",false)
                    var inext=Intent(this,TransactionInfoActivity::class.java)
                    inext.putExtras(bundle)
                    this.startActivity(inext)
//
//
//                }
//                1->{
//                    val alert: AlertDialog.Builder = AlertDialog.Builder(this)
//                    var layout=LinearLayout(this)
//                    layout.orientation=LinearLayout.VERTICAL
//                    var input1=EditText(this)
//                    input1.setHint("Name")
//                    alert.setTitle("Add a member?")
//                    alert.setMessage("Enter member name and email:")
//                    layout.addView(input1)
//                    var input2=EditText(this)
//                    input2.setHint("Email")
//                    layout.addView(input2)
//// Set an EditText view to get user input
//
//// Set an EditText view to get user input
//                    alert.setView(layout)
//
//                    alert.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->
//                        val value: Editable? = input1.text
//                        val email:Editable? = input2.text
//                        // Do something with value!
//
//
//
//
//                        var mem1=ExpMem()
//                        mem1.name=value.toString()
//                        mem1.email=email.toString()
//                        mainData.data[id1].members.add(mem1)
//                        sectionsPagerAdapter.memfrag.listmem.add(mem1.name)
//                        sectionsPagerAdapter.memfrag.memadapt!!.notifyDataSetChanged()
//                        for(l in mainData.data[id1].debt){
//                            l.add(0.0)
//                        }
//                        var newl:MutableList<Double> = mutableListOf<Double>()
//                        for(i in 0..(mainData.data[id1].debt).size){
//                            newl.add(0.0);
//                        }
//                        mainData.data[id1].debt.add(newl)
//                        writeData(mainData,filesDir)
//                        Snackbar.make(it, "We'll see", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show()
//                    })
//
//                    alert.setNegativeButton("Cancel",
//                        DialogInterface.OnClickListener { dialog, whichButton ->
//                            // Canceled.
//                            Snackbar.make(it, "Whatever.", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show()
//                        })
//
//                    alert.show()
//
//                }
//                2->{
//                    Snackbar.make(it, "What do you even want?", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show()
//                }
//            }
//        }
            //tabs!!.setupWithViewPager(viewPager)


        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.spgrp_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.newmem -> {
                val alert: AlertDialog.Builder = AlertDialog.Builder(this)
                var layout=LinearLayout(this)
                layout.orientation=LinearLayout.VERTICAL
                var input1=EditText(this)
                input1.setHint("Name")
                alert.setTitle("Add a member?")
                alert.setMessage("Enter member name and email:")
                layout.addView(input1)
                var input2=EditText(this)
                input2.setHint("Email")
                layout.addView(input2)
// Set an EditText view to get user input

// Set an EditText view to get user input
                alert.setView(layout)

                alert.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->
                    val value: Editable? = input1.text
                    val email:Editable? = input2.text
                    // Do something with value!




                    var mem1=ExpMem()
                    mem1.name=value.toString()
                    mem1.email=email.toString()
                    mainData.data[id1].members.add(mem1)

//                    sectionsPagerAdapter.memfrag.listmem.add(mem1.name)
//                    sectionsPagerAdapter.memfrag.memadapt!!.notifyDataSetChanged()
                    for(l in mainData.data[id1].debt){
                        l.add(0.0)
                    }
                    var newl:MutableList<Double> = mutableListOf<Double>()
                    for(i in 0..(mainData.data[id1].debt).size){
                        newl.add(0.0);
                    }
                    mainData.data[id1].debt.add(newl)
                    writeData(mainData,filesDir)
                    if(sectionsPagerAdapter.created){
                        sectionsPagerAdapter.memfrag.refresh()
                    }
//                    Snackbar.make(it, "We'll see", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show()
                })

                alert.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, whichButton ->
                        // Canceled.
//                        Snackbar.make(it, "Whatever.", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show()
                    })

                alert.show()

                true
            }
            R.id.newtr -> {
                val bundle = Bundle()
                bundle.putInt("id",id1)
                bundle.putBoolean("prompt",false)
                var inext=Intent(this,TransactionInfoActivity::class.java)
                inext.putExtras(bundle)
                this.startActivity(inext)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}