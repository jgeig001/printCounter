package com.jgeig001.printcounter.model.domain

import com.jgeig001.printcounter.model.domain.printTypes.ColorType
import com.jgeig001.printcounter.model.domain.printTypes.PaperType
import com.jgeig001.printcounter.model.domain.printTypes.QuantityType
import java.util.*

class PrintJob(
    var id: Int? = null,
    var timestamp: Date? = null,
    var pages: Int? = null,
    var paperType: PaperType? = null,
    var quantityType: QuantityType? = null,
    var colorType: ColorType? = null,
    var paid: Boolean? = null
) {

    fun isInitalized(): Boolean {
        return id != null && timestamp != null && pages != null && paperType != null && quantityType != null && colorType != null && paid != null
    }

}
