package nl.hu.dp;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static Connection connection = null;
    public static void main(String[] args) {
        System.out.println("Hello");
        try {
            testConnection();
        }
        catch (SQLException e){
            System.out.println(e);
        }
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
