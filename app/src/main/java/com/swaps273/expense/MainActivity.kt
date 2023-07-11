package com.swaps273.expense

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlin.collections.ArrayList
import com.swaps273.expense.databinding.ActivityMainBinding
import com.swaps273.expense.MainData
import com.swaps273.expense.*
import android.content.DialogInterface
import android.text.Editable

import android.widget.EditText
import android.widget.TextView


var mainData:MainData= MainData()
var arrayAdapter :ArrayAdapter<String>? = null
var l1:MutableList<String> = mutableListOf<String>()

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        var data11:MainData= MainData();
//        var e1= ExpGroup();
//        e1.name="YEAH, we r a grup."
//        var mem1=ExpMem()
//        mem1.name="Memba 1"
//        e1.members.add(mem1)
//        data11.data.add(e1)
//        writeData(data11,filesDir)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        mainData=readData(filesDir)
        val listView:ListView = findViewById(R.id.listview_first)
        l1= mutableListOf<String>()
        for(exp in mainData.data){
            l1.add(exp.name)
        }
        arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, l1)
        listView.adapter = arrayAdapter
        listView.emptyView=findViewById<TextView>(R.id.textView5)
        findViewById<TextView>(R.id.textView5).text="No groups yet. Add some."
        binding.fab.setOnClickListener {

            val alert: AlertDialog.Builder = AlertDialog.Builder(this)

            alert.setTitle("Add another group?")
            alert.setMessage("Enter group name")

// Set an EditText view to get user input

// Set an EditText view to get user input
            val input = EditText(this)
            alert.setView(input)

            alert.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->
                val value: Editable? = input.text
                // Do something with value!
                var g1 = ExpGroup()
                g1.name = value.toString()
                mainData.data.add(g1)
                l1.add(g1.name);
                arrayAdapter!!.notifyDataSetChanged()
                writeData(mainData, filesDir)
//                Snackbar.make(it, "New group added", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            })

            alert.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, whichButton ->
                    // Canceled.
                })

            alert.show()


        }

            //}
        //}


        listView.setOnItemClickListener { parent, v1, position, _ ->
            var i = Intent(this,SpecificGroupActivity::class.java)
            i.putExtra("id",position)
            this.startActivity(i)
        }






    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.newgrp -> {
                val alert: AlertDialog.Builder = AlertDialog.Builder(this)

                alert.setTitle("Add another group?")
                alert.setMessage("Enter group name")

// Set an EditText view to get user input

// Set an EditText view to get user input
                val input = EditText(this)
                alert.setView(input)

                alert.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->
                    val value: Editable? = input.text
                    // Do something with value!
                    var g1 = ExpGroup()
                    g1.name = value.toString()
                    mainData.data.add(g1)
                    l1.add(g1.name);
                    arrayAdapter!!.notifyDataSetChanged()
                    writeData(mainData, filesDir)
//                    Snackbar.make(it, "New group added", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show()
                })

                alert.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, whichButton ->
                        // Canceled.
                    })

                alert.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}