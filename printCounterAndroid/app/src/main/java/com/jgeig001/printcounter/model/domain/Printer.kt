package com.jgeig001.printcounter.model.domain

import com.google.gson.annotations.SerializedName
import com.jgeig001.printcounter.model.domain.printTypes.PrintType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class Printer(
    val id: Int,
    var name: String,
    val hasColor: Boolean,
    val costs: List<Cost>,
    val printJobs: List<PrintJob> = emptyList(),
    @SerializedName("alive")
    var isAlive: Boolean = true
) {

    private lateinit var costsMap: HashMap<PrintType, MutableList<Pair<Date, Double>>>

    fun getCostOf(): Double {
        if (!this::costsMap.isInitialized) {
            setupCostsMap()
        }
        // TODO: impl.
        return .0;
    }

    fun hasNoColor(): Boolean {
        return !hasColor
    }

    private fun setupCostsMap() {
        for (cost in costs) {
            val date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(cost.timestamp)
            val pair = Pair<Date, Double>(date, cost.cent)
            val curPrintType = cost.thisPrintType
            if (costsMap.containsKey(curPrintType)) {
                costsMap[curPrintType]?.add(pair)
            } else {
                costsMap[curPrintType] = mutableListOf(pair)
            }
        }
    }


}