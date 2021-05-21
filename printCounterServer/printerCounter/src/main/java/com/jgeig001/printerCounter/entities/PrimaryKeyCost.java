package com.jgeig001.printerCounter.entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * defines attributes of primary key of an cost entity
 */
public class PrimaryKeyCost implements Serializable {
    private PrinterEntity fPrinter;
    private int printType;
    private Timestamp timestamp;
}
