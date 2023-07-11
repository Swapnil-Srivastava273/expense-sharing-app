package com.swaps273.expense
class ExpTransaction{
    var from:Int =0
    var total:Double=0.0
    var amount:MutableList<Double> = mutableListOf<Double>()
    var reason:String=""

}
class ExpMem{
    var name: String=""
    var email:String=""
    var id: Int=0

}
class ExpGroup{
    var name: String=""
    var members: MutableList<ExpMem> = mutableListOf<ExpMem>()
    var history: MutableList<ExpTransaction> = mutableListOf<ExpTransaction>()
    var debt: MutableList<MutableList<Double>> = mutableListOf<MutableList<Double>>()



}
class MainData {
    var data: MutableList<ExpGroup> = mutableListOf<ExpGroup>()
}