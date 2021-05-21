package com.jgeig001.printcounter.ui.main.print.viewObjects

import com.jgeig001.printcounter.model.domain.User
import com.jgeig001.printcounter.model.domain.printTypes.ColorType
import com.jgeig001.printcounter.model.domain.printTypes.PaperType
import com.jgeig001.printcounter.model.domain.printTypes.QuantityType
import java.util.*

class PrintJobData(
    var orderer: User? = null,
    var printerWithOwner: PrinterWithOwner? = null,
    var colorType: ColorType? = null,
    var paperType: PaperType? = null,
    var quantityType: QuantityType? = null,
    var pagesAmount: Int? = null,
    var timestamp: Date? = null
) {
    fun setTimestamp() {
        timestamp = Calendar.getInstance().time
    }

    fun isValid(): Boolean {
        val notNull =
            printerWithOwner != null && colorType != null && paperType != null && quantityType != null && pagesAmount != null && orderer != null && timestamp != null
        val colorMismatch = printerWithOwner!!.hasNoColor() && this.colorType == ColorType.COLORED
        return notNull && !colorMismatch
    }
}
