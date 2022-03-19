package nl.hu.dp.domains;


import nl.hu.dp.dao.OVChipkaartDAO;

import java.sql.Date;

public class OVChipkaart {
    private int kaart_nummer;
    private java.sql.Date geldig_tot = new java.sql.Date(0);
    private int klasse;
    private double saldo;
    private Reiziger reiziger;

    public OVChipkaart(){
    }

    public OVChipkaart(int kaart_nummer, java.sql.Date geldig_tot, int klasse, double saldo){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    @Override
    public String toString(){
        String s = "";
        s = String.format("OVChipkaart: %d, %d, %.2f, %s", this.kaart_nummer, this.klasse, this.saldo, this.geldig_tot);
        return s;
    }
}
