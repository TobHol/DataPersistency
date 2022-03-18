package nl.hu.dp;

import nl.hu.dp.dao.AdresDAO;
import nl.hu.dp.dao.AdresDAOsql;
import nl.hu.dp.dao.ReizigerDAO;
import nl.hu.dp.dao.ReizigerDAOPsql;
import nl.hu.dp.domains.Adres;
import nl.hu.dp.domains.Reiziger;

import java.sql.*;
import java.util.List;


public class Main {
    private static Connection connection = null;
    public static void main(String[] args) throws SQLException{
        System.out.println("Tobias Holscher");

        ReizigerDAOPsql rdp = new ReizigerDAOPsql(getConnection());
        AdresDAOsql adp = new AdresDAOsql(getConnection());
        rdp.setAdao(adp);
        adp.setRdao(rdp);

        testReizigerDAO(rdp);
        testAdresDAO(rdp);
    }

    private static void testAdresDAO(ReizigerDAO rdao) throws SQLException{
        System.out.println("\n---------- Test AdresDAOSql -------------");

//      Current state van tabel
        List<Adres> adressen = rdao.getAdao().findAll();
        System.out.println("[Testing findAll()] Gives following adresses:");
        for (Adres r : adressen) {
            System.out.println(r);
        }
        System.out.println();

//      Test Delete Database
        String gbdatum = "1981-03-14";
        Reiziger aika = new Reiziger(88, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        rdao.delete(aika);
        Adres a1 = new Adres(3000, "5683fd", "22a", "Roomstraat", "Helmondus");
        rdao.getAdao().delete(a1);

//      Create Relations
        a1.setReiziger(aika);
        aika.setAdres(a1);

//      Persist Data
        System.out.println("[Testing insert] \nFirst " + adressen.size() + " adressen \nAfter AdreDAOsql.save() ");
        rdao.save(aika);
        adressen = rdao.getAdao().findAll();
        System.out.println("\n"+adressen.size() + " adressen\n");

//      Test Update
        System.out.println("\n[Testing update]: Helmondus vervangen door Geldropus \n");
        System.out.println("Before update: " + a1.toString() + "\n");
        a1.setWoonplaats("Geldropus \n");
        rdao.getAdao().update(a1);
        System.out.println("After update:" + rdao.getAdao().findById(a1.getAdres_id()).toString() + "\n");

//      Test Delete
        rdao.getAdao().delete(a1);
        rdao.delete(aika);

//      Current state van tabel
        adressen = rdao.getAdao().findAll();
        System.out.println("[Testing findAll()] Gives following adresses: \n");
        for (Adres r : adressen) {
            System.out.println(r);
        }

        System.out.println("\n---------- Test Complete--------------");
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAOSql -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Testing findAll()] Gives following reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Testing insert] \nFirst " + reizigers.size() + " reizigers, \nAfter ReizigerDAOSql.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println("\n"+reizigers.size() + " reizigers\n");

        // Test update
        System.out.println("\n[Testing update]: check last name of person with ID: 77");
        sietske.setAchternaam("Zus");
        rdao.update(sietske);
        reizigers = rdao.findAll();
        for(Reiziger r : reizigers){
            System.out.println(r.toString());
        }

        //Test delete
        System.out.println("\n[Testing delete]: check last name of person with ID: 77, oh wait he's gone!");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        for(Reiziger r : reizigers){
            System.out.println(r.toString());
        }

        System.out.println("\n---------- Test Complete--------------");
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null){
            String dbName= "ovchip";
            String uName= "postgres";
            String pWord= "haha";
            String port = ":5432";

            String url = String.format("jdbc:postgresql://localhost:5432/%s?user=%s&password=%s",
                    dbName, uName, pWord);
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    private static void closeConnection() throws SQLException{
        if(connection != null){
            connection.close();
            connection=null;
        }
    }

    private static void testConnection() throws SQLException{
        getConnection();
        String query = "SELECT * FROM reiziger;";
        PreparedStatement prepQuery = connection.prepareStatement(query);
        ResultSet set = prepQuery.executeQuery();

        String output = "Alle reizigers: ";
        System.out.println(output);

        while(set != null && set.next()){
            output = " \t ";
            String id = set.getString("Reiziger_id");
            String voorletter = set.getString("voorletters");
            String tussenvoegsel = set.getString("tussenvoegsel");
            String achternaam = set.getString("achternaam");
            String geboorteDatum = set.getString("geboortedatum");
            if (tussenvoegsel == null){
                output += String.format(" #%s %s. %s (%s)", id, voorletter, achternaam, geboorteDatum);
            }
            else {
                output += String.format(" #%s %s. %s %s (%s)", id, voorletter, tussenvoegsel, achternaam, geboorteDatum);
            }
            System.out.println(output);
        }

        closeConnection();
    }
}
