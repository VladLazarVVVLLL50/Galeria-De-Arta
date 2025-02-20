package com.vlad.galeriedearta.model;

import java.util.Date;
import java.util.List;

public class OperaDeArta {
    private Long operaDeArtaID;
    private String titlu;
    private String tip; // pictura, sculptura, fotografie, alt_tip
    private String dimensiune;
    private Date dataCreare;

    private List<Long> artisti;

    // Constructor fără parametri
    public OperaDeArta() {
    }

    // Constructor cu parametri
    public OperaDeArta(Long operaDeArtaID, String titlu, String tip, String dimensiune, Date dataCreare, List<Long> artisti) {
        this.operaDeArtaID = operaDeArtaID;
        this.titlu = titlu;
        this.tip = tip;
        this.dimensiune = dimensiune;
        this.dataCreare = dataCreare;
        this.artisti = artisti;
    }

    // Getter și Setter pentru operaDeArtaID
    public Long getOperaDeArtaID() {
        return operaDeArtaID;
    }

    public void setOperaDeArtaID(Long operaDeArtaID) {
        this.operaDeArtaID = operaDeArtaID;
    }

    // Getter și Setter pentru titlu
    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    // Getter și Setter pentru tip
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    // Getter și Setter pentru dimensiune
    public String getDimensiune() {
        return dimensiune;
    }

    public void setDimensiune(String dimensiune) {
        this.dimensiune = dimensiune;
    }

    // Getter și Setter pentru dataCreare
    public Date getDataCreare() {
        return dataCreare;
    }

    public void setDataCreare(Date dataCreare) {
        this.dataCreare = dataCreare;
    }

    public List<Long> getArtisti() {
        return artisti;
    }

    public void setArtisti(List<Long> artisti) {
        this.artisti = artisti;
    }

    @Override
    public String toString() {
        return "OperaDeArta{" +
                "operaDeArtaID=" + operaDeArtaID +
                ", titlu='" + titlu + '\'' +
                ", tip='" + tip + '\'' +
                ", dimensiune='" + dimensiune + '\'' +
                ", dataCreare=" + dataCreare +
                '}';
    }

}
