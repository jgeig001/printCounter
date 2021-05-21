package com.jgeig001.printerCounter.entities.specialEntities;

import com.jgeig001.printerCounter.entities.CostEntity;
import com.jgeig001.printerCounter.entities.PrinterEntity;

import java.util.List;

public class PrinterWithOwner {

    private String ownerName;
    private int printerId;
    private String name;
    private Boolean hasColor;
    private List<CostEntity> costs;
    private Boolean isAlive = true;

    public PrinterWithOwner(String ownerName, PrinterEntity printer) {
        this.ownerName = ownerName;
        this.printerId = printer.getId();
        this.name = printer.getName();
        this.hasColor = printer.isHasColor();
        this.costs = printer.getCosts();
        this.isAlive = printer.isAlive();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getPrinterId() {
        return printerId;
    }

    public void setPrinterId(int printerId) {
        this.printerId = printerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasColor() {
        return hasColor;
    }

    public void setHasColor(Boolean hasColor) {
        this.hasColor = hasColor;
    }

    public List<CostEntity> getCosts() {
        return costs;
    }

    public void setCosts(List<CostEntity> costs) {
        this.costs = costs;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
}
