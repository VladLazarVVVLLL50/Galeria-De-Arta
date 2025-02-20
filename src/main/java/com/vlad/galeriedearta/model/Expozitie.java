package com.vlad.galeriedearta.model;

import java.time.LocalDate;

public class Expozitie {
    private int expozitieID;
    private String nume;
    private String descriere;
    private LocalDate dataInceput;
    private LocalDate dataSfarsit;
    private int locatieID; // ID-ul locației unde se desfășoară expoziția
    private int curatorID; // ID-ul curatorului responsabil pentru expoziție

    public Expozitie() {
    }

    public Expozitie(int expozitieID, String nume, String descriere, LocalDate dataInceput, LocalDate dataSfarsit, int locatieID, int curatorID) {
        this.expozitieID = expozitieID;
        this.nume = nume;
        this.descriere = descriere;
        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
        this.locatieID = locatieID;
        this.curatorID = curatorID;
    }

    // Getteri și setteri
    public int getExpozitieID() {
        return expozitieID;
    }

    public void setExpozitieID(int expozitieID) {
        this.expozitieID = expozitieID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public LocalDate getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(LocalDate dataInceput) {
        this.dataInceput = dataInceput;
    }

    public LocalDate getDataSfarsit() {
        return dataSfarsit;
    }

    public void setDataSfarsit(LocalDate dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

    public int getLocatieID() {
        return locatieID;
    }

    public void setLocatieID(int locatieID) {
        this.locatieID = locatieID;
    }

    public int getCuratorID() {
        return curatorID;
    }

    public void setCuratorID(int curatorID) {
        this.curatorID = curatorID;
    }
}
