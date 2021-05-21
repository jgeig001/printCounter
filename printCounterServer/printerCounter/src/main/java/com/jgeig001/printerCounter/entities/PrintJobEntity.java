package com.jgeig001.printerCounter.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jgeig001.printerCounter.Exceptions.NoColorPrinterException;
import com.jgeig001.printerCounter.model.types.PrintType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "print_jobs")
public class PrintJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "printjob_id")
    private int id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp timestamp;
    private int pages;
    private int quantityType;
    private int paperType;
    private int colorType;

    @Column(columnDefinition = "boolean default false")
    private boolean paid;

    @ManyToOne // mapped to db
    @JsonBackReference
    private UserEntity fOrderer;

    @ManyToOne // mapped to db
    @JsonBackReference
    private PrinterEntity fPrinter;

    public PrintJobEntity() {
    }

    public PrintJobEntity(int pages, PrintType quantityType, PrintType paperType, PrintType colorType,
                          UserEntity orderer, PrinterEntity printer) throws NoColorPrinterException {
        if (colorType == PrintType.COLORED && !printer.isHasColor()) {
            throw new NoColorPrinterException("printer can not print colors as ordered");
        }
        this.pages = pages;
        this.quantityType = quantityType.getValue();
        this.paperType = paperType.getValue();
        this.colorType = colorType.getValue();
        this.fOrderer = orderer;
        this.fPrinter = printer;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // getter & setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public int getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(int quantityType) {
        this.quantityType = quantityType;
    }

    public int getPaperType() {
        return paperType;
    }

    public void setPaperType(int paperType) {
        this.paperType = paperType;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public UserEntity getfOrderer() {
        return fOrderer;
    }

    public void setfOrderer(UserEntity fOrderer) {
        this.fOrderer = fOrderer;
    }

    public PrinterEntity getfPrinter() {
        return fPrinter;
    }

    public void setfPrinter(PrinterEntity fPrinter) {
        this.fPrinter = fPrinter;
    }

    @Override
    public String toString() {
        return "PrintJobEntity{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", pages=" + pages +
                ", paid=" + paid +
                ", quantityType=" + quantityType +
                ", paperType=" + paperType +
                ", colorType=" + colorType +
                ", orderer=" + fOrderer.getName() +
                ", printerEntity=" + fPrinter.getName() +
                '}';
    }
}
