package com.jgeig001.printcounter.model.domain.printTypes

import com.google.gson.annotations.SerializedName

enum class ColorType(val value: Int, val description: String) : PrintType {

    @SerializedName("300")
    BLACK(300, "schwarz-weiß"),

    @SerializedName("301")
    COLORED(301, "bunt");

    companion object {
        fun getTypeBy(value: Int): ColorType? {
            for (type in ColorType.values()) {
                if (type.value == value) {
                    return type
                }
            }
            return null
        }

        fun getTypeBy(valueString: String): ColorType? {
            return getTypeBy(valueString.toInt())
        }
    }


}