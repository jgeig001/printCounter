package com.jgeig001.printcounter.model.domain

import com.google.gson.annotations.SerializedName
import com.jgeig001.printcounter.model.domain.printTypes.*

class Cost(
    @SerializedName("printType")
    private val printTypeInt: Int,
    val cent: Double,
    val timestamp: String
) {

    val thisPrintType: PrintType

    init {
        val anyType = listOf<PrintType?>(
            PaperType.getTypeBy(printTypeInt),
            QuantityType.getTypeBy(printTypeInt),
            ColorType.getTypeBy(printTypeInt),
        )
        val justOne = anyType.filter { type -> type != null }
        if (justOne.size == 1) {
            thisPrintType = justOne.first()
                ?: throw PrintTypeException("no matching PrintType found")
        } else {
            throw PrintTypeException(justOne.size.toString() + "types matched: has to be just one")
        }
    }

}