package com.jgeig001.printerCounter.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jgeig001.printerCounter.model.types.PrintType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "costs")
@IdClass(PrimaryKeyCost.class)
public class CostEntity {

    @Id
    @ManyToOne // mapped to db
    @JoinColumn(name = "printer_id")
    @JsonBackReference
    private PrinterEntity fPrinter;

    @Id
    // use Integer instead of Printtype;
    // so in db the Printtype.value is used instead of the ordinal number.
    private int printType;

    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp timestamp;

    private float cent;

    public CostEntity() {
    }

    public CostEntity(PrinterEntity fPrinter, PrintType printType, float cent) {
        this.fPrinter = fPrinter;
        this.printType = printType.getValue();
        this.cent = cent;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // getter & setter
    public PrinterEntity getfPrinter() {
        return fPrinter;
    }

    public void setfPrinter(PrinterEntity fPrinter) {
        this.fPrinter = fPrinter;
    }

    public int getPrintType() {
        return printType;
    }

    public void setPrintType(int printType) {
        this.printType = printType;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public float getCent() {
        return cent;
    }

    public void setCent(float cent) {
        this.cent = cent;
    }

}
