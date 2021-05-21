package com.jgeig001.printcounter.ui.main.print.viewObjects

import com.jgeig001.printcounter.model.domain.Cost
import com.jgeig001.printcounter.model.domain.Printer

data class PrinterWithOwner(
    val ownerName: String,
    val printerId: Int,
    var name: String,
    val hasColor: Boolean,
    val costs: List<Cost>,
    var isAlive: Boolean = true
) {

    private lateinit var printer: Printer

    fun getPrinter(): Printer {
        if (!this::printer.isInitialized) {
            this.printer = Printer(printerId, name, hasColor, costs, isAlive = isAlive)
        }
        return this.printer;
    }

    fun hasNoColor(): Boolean {
        return !hasColor
    }


}