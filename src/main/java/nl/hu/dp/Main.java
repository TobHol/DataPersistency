package nl.hu.dp;

import nl.hu.dp.dao.ReizigerDAO;
import nl.hu.dp.domains.Reiziger;

import java.sql.*;
import java.util.List;


public class Main {
    private static Connection connection = null;
    public static void main(String[] args) throws SQLException{
        System.out.println("Tobias Holscher");

        ReizigerDAOPsql rdp = new ReizigerDAOPsql(getConnection());

        testReizigerDAO(rdp);
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
