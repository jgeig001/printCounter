package com.jgeig001.printcounter.model.domain.printTypes

import com.google.gson.annotations.SerializedName

enum class PaperType(val value: Int, val description: String) : PrintType {

    @SerializedName("100")
    PAPER_HIGH_QUALITY(100, "hoch"),

    @SerializedName("101")
    PAPER_NORMAL(101, "normal"),

    @SerializedName("102")
    PAPER_ECO(102, "öko");

    companion object {
        fun getTypeBy(value: Int): PaperType? {
            for (type in PaperType.values()) {
                if (type.value == value) {
                    return type
                }
            }
            return null
        }

        fun getTypeBy(valueString: String): PaperType? {
            return getTypeBy(valueString.toInt())
        }
    }
}