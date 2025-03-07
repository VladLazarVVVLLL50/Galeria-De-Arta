package com.vlad.galeriedearta.model;

import java.util.Date;
import java.util.List;

public class Artist {
    private Long artistID;
    private String nume;
    private String prenume;
    private Date dataNastere;
    private Date dataDeces;
    private String nationalitate;
    private String biografie;
    private String email;
    private String numarTelefon;
    private String parola;

    private List<Long> opereDeArta;

    public Artist() {
    }

    public Artist(Long artistID, String nume, String prenume, Date dataNastere, Date dataDeces, String nationalitate, String biografie, String email, String numarTelefon, String parola, List<Long> opereDeArta) {
        this.artistID = artistID;
        this.nume = nume;
        this.prenume = prenume;
        this.dataNastere = dataNastere;
        this.dataDeces = dataDeces;
        this.nationalitate = nationalitate;
        this.biografie = biografie;
        this.email = email;
        this.numarTelefon = numarTelefon;
        this.parola = parola;
        this.opereDeArta = opereDeArta;
    }


    public Long getArtistID() {
        return artistID;
    }

    public void setArtistID(Long artistID) {
        this.artistID = artistID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Date getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(Date dataNastere) {
        this.dataNastere = dataNastere;
    }

    public Date getDataDeces() {
        return dataDeces;
    }

    public void setDataDeces(Date dataDeces) {
        this.dataDeces = dataDeces;
    }

    public String getNationalitate() {
        return nationalitate;
    }

    public void setNationalitate(String nationalitate) {
        this.nationalitate = nationalitate;
    }

    public String getBiografie() {
        return biografie;
    }

    public void setBiografie(String biografie) {
        this.biografie = biografie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public List<Long> getOpereDeArta() {
        return opereDeArta;
    }

    public void setOpereDeArta(List<Long> opereDeArta) {
        this.opereDeArta = opereDeArta;
    }
}
