package com.jgeig001.printerCounter.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jgeig001.printerCounter.model.types.PrintType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "printer")
public class PrinterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "printer_id")
    private int id;

    private String name;

    @Column(columnDefinition = "boolean default true")
    private boolean alive;

    private boolean hasColor = true;

    @ManyToOne
    @JsonBackReference
    private UserEntity fOwner;//

    @OneToMany(mappedBy = "fPrinter") // NOT mapped to db
    @JsonManagedReference
    private List<PrintJobEntity> printJobs;//

    // costs per PrintType in cent
    @OneToMany(mappedBy = "fPrinter") // NOT mapped to db
    @JsonManagedReference
    private List<CostEntity> costs;

    public PrinterEntity(String name, UserEntity owner) {

    }

    public PrinterEntity(UserEntity fOwner, String name) {
        this.name = name;
        this.alive = true;
        this.fOwner = fOwner;
        this.printJobs = new ArrayList<>();
        this.costs = new ArrayList<>();
    }

    public PrinterEntity() {
    }


    public void addCostEntity(CostEntity costEntity) {
        costs.add(costEntity);
    }

    // getter & setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getfOwner() {
        return fOwner;
    }

    public void setfOwner(UserEntity fOwner) {
        this.fOwner = fOwner;
    }

    public List<PrintJobEntity> getPrintJobs() {
        return printJobs;
    }

    public void setPrintJobs(List<PrintJobEntity> printJobs) {
        this.printJobs = printJobs;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isHasColor() {
        return hasColor;
    }

    public void setHasColor(boolean hasColor) {
        this.hasColor = hasColor;
    }

    public List<CostEntity> getCosts() {
        return costs;
    }

    public void setCosts(List<CostEntity> costs) {
        this.costs = costs;
    }

    public void updateHasColor() {
        if (costs.stream().anyMatch(costEntity -> costEntity.getPrintType() == PrintType.COLORED.getValue())) {
            hasColor = true;
        } else {
            hasColor = false;
        }
    }

    @Override
    public String toString() {
        return "PrinterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                //", owner=" + owner.getName() +
                '}';
    }

}
