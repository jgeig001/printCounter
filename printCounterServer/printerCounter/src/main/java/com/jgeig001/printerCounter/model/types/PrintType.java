package com.jgeig001.printerCounter.model.types;

public enum PrintType {

    // the used paper type
    PAPER_HIGH_QUALITY(100),
    PAPER_NORMAL(101),
    PAPER_ECO(102),

    // how much was printed
    QUANTITY_MUCH(200),
    QUANTITY_AVERAGE(201),
    QUANTITY_LITTLE(202),

    // where colors used
    BLACK(300),
    COLORED(301);

    private final int value;

    PrintType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PrintType getTypeBy(int value) {
        for (PrintType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    public static PrintType getTypeBy(String valueString) {
        return getTypeBy(Integer.parseInt(valueString));
    }

}


