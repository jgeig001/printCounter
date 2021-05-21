package com.jgeig001.printerCounter.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    private String name;

    /**
     * HOW TO: implement 1-n relation
     * https://en.wikibooks.org/wiki/Java_Persistence/OneToMany
     */
    @OneToMany(mappedBy = "fOwner") // NOT mapped to db
    @JsonManagedReference
    private List<PrinterEntity> ownedPrinterEntities;

    @OneToMany(mappedBy = "fOrderer") // NOT mapped to db
    @JsonManagedReference
    private List<PrintJobEntity> donePrintJobs;

    public UserEntity() {
    }

    public UserEntity(String name) {
        this.name = name;
        ownedPrinterEntities = new ArrayList<>();
        donePrintJobs = new ArrayList<>();
    }

    public void addPrinterEntitie(PrinterEntity printerEntity) {
        this.ownedPrinterEntities.add(printerEntity);
        printerEntity.setfOwner(this);
    }

    // ------------------------ getter & setter ------------------------------------------------------------------------
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

    public List<PrinterEntity> getOwnedPrinterEntities() {
        return ownedPrinterEntities;
    }

    public void setOwnedPrinterEntities(List<PrinterEntity> ownedPrinterEntities) {
        this.ownedPrinterEntities = ownedPrinterEntities;
    }

    public List<PrintJobEntity> getDonePrintJobs() {
        return donePrintJobs;
    }

    public void setDonePrintJobs(List<PrintJobEntity> donePrintJobs) {
        this.donePrintJobs = donePrintJobs;
    }

    public boolean hasNoPrintJobsDone() {
        return donePrintJobs.isEmpty();
    }

    public boolean hasNoPrinter() {
        return ownedPrinterEntities.isEmpty();
    }

    // -------------------------------- Override -----------------------------------------------------------------------

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                //", ownedPrinterEntities=" + ownedPrinterEntities +
                //", donePrintJobs=" + donePrintJobs +
                '}';
    }
}

