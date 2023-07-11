package com.swaps273.expense
import android.os.Bundle
import java.io.*
import android.content.Context
import com.swaps273.expense.MainData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
fun readFile(f:File): String {
    if(!File(f,"example.txt").exists()) writeFile("{}",f)
    val bufferedReader: BufferedReader = File(f,"example.txt").bufferedReader()
    var t:String= bufferedReader.use { it.readText() }
    return t;

}
fun readData(f: File) : MainData {
    val gson=Gson()
    return gson.fromJson(readFile(f),MainData::class.java)
}
fun writeFile(data1: String, f:File){
    File(f,"example.txt").writeText(data1)

}
fun writeData(data: MainData,f:File){
    val gson=Gson()
    writeFile(gson.toJson(data),f)
}
