package com.swaps273.expense

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.preference.PreferenceFragmentCompat
import kotlin.math.abs

class InfoCardAdapter(var context: Context, var arr:ArrayList<String>,var promptlist:MutableList<Double>?): BaseAdapter() {
    private var values:HashMap<Int,Double> =HashMap<Int,Double>()
    fun init(){
        for(i in 0..(arr.size)){
            if(i>=promptlist!!.size)values.put(i,0.0)
            else values.put(i,promptlist!![i])
        }
    }

    override fun getItem(pos: Int): Any {
        return arr.get(pos)
    }
    override fun getItemId(pos:Int):Long{
        return pos.toLong();
    }

    override fun getCount(): Int {
        return arr.size
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view=View.inflate(context,R.layout.infocard,null)
        var name=arr.get(p0)
        var txt: TextView =view.findViewById(R.id.textView3)
        txt.text=name
        var edit:EditText=view.findViewById(R.id.editTextNumber)
        if(p0<=promptlist!!.size-1){
            edit.setText(promptlist!![p0]!!.toString())
        }

        edit.doOnTextChanged { text, start, before, count ->
            if(text.toString()=="")values.replace(p0,0.0)
            else values.replace(p0,text.toString().toDouble())
         }
        return view
    }
    fun getValue(pos:Int):Double{
        return values.get(pos)!!;
    }
}
class TransactionInfoActivity : AppCompatActivity() {
    private var infolv: ListView?=null
    private var spin:Spinner?=null
    private var spin4:Spinner?=null
    private var grpid:Int=-1
    private var sub:Button?=null
    private var prompt=false
    private var trval=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
//        if (savedInstanceState == null) {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id., SettingsFragment())
//                .commit()
//        }
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        grpid=intent.getIntExtra("id",-1)
        prompt=intent.getBooleanExtra("prompt",false)
        var prlist= mutableListOf<Double>()
        var trdata:ExpTransaction?=null
        if(prompt){
            trval=intent.getIntExtra("trval",-1)
            trdata = mainData.data[id1].history[trval]
            prlist=trdata.amount
        }
        var grp: ExpGroup= mainData.data[grpid]
        var listmem= arrayListOf<String>()
        for(i in grp.members){
            listmem.add(i.name)
        }

        var ad=InfoCardAdapter(applicationContext, listmem,prlist)
        ad.init()
        infolv=findViewById(R.id.infolist)
        spin=findViewById(R.id.spinner3)
        spin4=findViewById(R.id.spinner4)
        sub=findViewById(R.id.button)
        var reason:String=findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        spin?.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listmem)
        spin4?.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,arrayListOf("Split by amounts:","Split by share:"))
        infolv?.adapter=ad
        if(prompt)  {
            findViewById<EditText>(R.id.editTextTextPersonName).setText(trdata!!.reason)
            spin?.setSelection(trdata!!.from)
            sub?.text="Edit"
            findViewById<EditText>(R.id.editTextNumberDecimal).setText(trdata!!.total.toString())

        }
        sub?.setOnClickListener{
            var amt:EditText=findViewById(R.id.editTextNumberDecimal)
            var tot:Double=amt.text.toString().toDouble()
            var topay: Int =spin!!.selectedItemPosition
            var costlist= mutableListOf<Double>()
            if(spin4?.selectedItemPosition==0){

                var rest:Double=0.0
                for( l in 0..(ad.count-1)){

                    rest+=ad.getValue(l)
                }
                if(abs(rest-tot)<0.01){
                    for(i in 0..(ad.count-1)){
                        var cost=ad.getValue(i)
                        mainData.data[id1].debt[topay][i]-=cost
                        mainData.data[id1].debt[i][topay]+=cost
                        if(prompt){
                            if(trdata!!.amount.size-1>=i){
                                mainData.data[id1].debt[trdata.from][i]+=trdata.amount[i];
                                mainData.data[id1].debt[i][trdata.from]-=trdata.amount[i];
                            }
                        }
                        costlist.add(cost);
                    }
                    var t1=ExpTransaction()
                    t1.from=topay
                    t1.amount=costlist
                    t1.total=tot
                    t1.reason=findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                    if(prompt){
                        mainData.data[id1].history[trval]=t1
                    } else mainData.data[id1].history.add(t1)
                    writeData(mainData,filesDir)
                    finish()
                }else{
                    val alert: AlertDialog.Builder = AlertDialog.Builder(this)
                    alert.setTitle("Error")
                    alert.setMessage("The values don't add up.")
                    alert.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->{}})
                    alert.show()
                }

            }else{
                var ratio:Double=0.0
                for( l in 0..(ad.count-1)){
                    ratio+=ad.getValue(l)
                }
                var split=tot/ratio

                for(i in 0..(ad.count-1)){
                    var cost=ad.getValue(i)*split
                    mainData.data[id1].debt[topay][i]-=cost
                    mainData.data[id1].debt[i][topay]+=cost
                    if(prompt){
                        if(trdata!!.amount.size-1>=i){
                            mainData.data[id1].debt[trdata.from][i]+=trdata.amount[i];
                            mainData.data[id1].debt[i][trdata.from]-=trdata.amount[i];
                        }
                    }
                    costlist.add(cost)
                }
                var t1=ExpTransaction()
                t1.from=topay
                t1.amount=costlist
                t1.total=tot
                t1.reason=findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
                if(prompt){
                    mainData.data[id1].history[trval]=t1
                }else mainData.data[id1].history.add(t1)
                writeData(mainData,filesDir)
                finish()
            }
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}