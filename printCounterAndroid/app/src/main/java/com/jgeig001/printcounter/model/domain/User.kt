package com.jgeig001.printcounter.model.domain

import com.google.gson.annotations.SerializedName

class User(
    val id: Int,
    var name: String,
    @SerializedName("ownedPrinterEntities")
    val ownedPrinters: List<Printer> = emptyList(),
    @SerializedName("donePrintJobs")
    val printJobs: List<PrintJob> = emptyList()
) {

    override fun toString(): String {
        return "#USER#  id:" + id +
                "  name:" + name +
                "  ownedPrinters:" + ownedPrinters +
                "  printJobs:" + printJobs
    }
}