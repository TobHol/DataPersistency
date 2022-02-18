package nl.hu.dp.domains;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int id = 0;
    private String voorletters ="";
    private String tussenvoegsel = "";
    private String achternaam = "";
    private java.sql.Date geboortedatum = new java.sql.Date(0);

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum){
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger(){}


    public String toString(){
        return "Reziger:\t(" + this.id + " " + this.voorletters + " " + this.achternaam + " " + this.tussenvoegsel + " " + this.geboortedatum.toString() + ")";
    }

    public java.sql.Date getGeboortedatum() {
        return geboortedatum;
    }

    public int getId() {
        return id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        if(achternaam != null){
            if(achternaam.length() > 255){
                achternaam = achternaam.substring(0, 255);
            }
            this.achternaam = achternaam;
        }
    }

    public void setGeboortedatum(Date geboortedatum) {
        if(geboortedatum != null){
            if(geboortedatum.after(new Date(0)))
            this.geboortedatum = geboortedatum;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        if(tussenvoegsel != null){
            if(tussenvoegsel.length() > 10){
                tussenvoegsel = tussenvoegsel.substring(0, 10);
            }
            this.tussenvoegsel = tussenvoegsel;
        }
    }

    public void setVoorletters(String voorletters) {
        if(voorletters != null){
            if(voorletters.length()> 10){
                voorletters = voorletters.substring(0,10);
            }
            this.voorletters = voorletters;
        }
    }
}
