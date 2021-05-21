package com.jgeig001.printcounter.model.domain.printTypes

import com.google.gson.annotations.SerializedName

enum class QuantityType(val value: Int, val description: String) : PrintType {

    @SerializedName("200")
    QUANTITY_MUCH(200, "viel"),

    @SerializedName("201")
    QUANTITY_AVERAGE(201, "normal"),

    @SerializedName("202")
    QUANTITY_LITTLE(202, "wenig");

    companion object {
        fun getTypeBy(value: Int): QuantityType? {
            for (type in QuantityType.values()) {
                if (type.value == value) {
                    return type
                }
            }
            return null
        }

        fun getTypeBy(valueString: String): QuantityType? {
            return getTypeBy(valueString.toInt())
        }
    }
}