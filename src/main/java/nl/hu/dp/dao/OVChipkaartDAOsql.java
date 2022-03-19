package nl.hu.dp.dao;

import nl.hu.dp.domains.Adres;
import nl.hu.dp.domains.OVChipkaart;
import nl.hu.dp.domains.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOsql implements OVChipkaartDAO{
    private Connection conn;

    public OVChipkaartDAOsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean update(OVChipkaart inOVChipkaart) throws SQLException {
        return false;
    }

    @Override
    public boolean save(OVChipkaart inOVChipkaart) throws SQLException {
        OVChipkaart ovChipkaart = null;
        try{
            PreparedStatement preparedStatement =  conn.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer, saldo, geldig_tot, klasse, reiziger_id) VALUES (?,?,?,?,?);");
            preparedStatement.setInt(1, inOVChipkaart.getKaart_nummer());
            preparedStatement.setDouble(2, inOVChipkaart.getSaldo());
            preparedStatement.setDate(3, inOVChipkaart.getGeldig_tot());
            preparedStatement.setInt(4, inOVChipkaart.getKlasse());
            preparedStatement.setInt(5, inOVChipkaart.getReiziger().getId());
            preparedStatement.executeQuery();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(OVChipkaart inOVChipkaart) throws SQLException {
        OVChipkaart ovChipkaart = null;
        try{
            PreparedStatement preparedStatement =  conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?;");
            preparedStatement.setInt(1, inOVChipkaart.getKaart_nummer());
            preparedStatement.executeQuery();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        ArrayList<OVChipkaart> ovChipkaarten = new ArrayList<OVChipkaart>();
        try{
            PreparedStatement preparedStatement =  conn.prepareStatement("SELECT * FROM ov_chipkaart;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet != null && resultSet.next()){
                ovChipkaarten.add(parseStatement(resultSet));
            }

            resultSet.close();
            return ovChipkaarten;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }


    @Override
    public OVChipkaart findById(int id) throws SQLException {
        OVChipkaart ovChipkaart = null;
        try{
            PreparedStatement preparedStatement =  conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet != null && resultSet.next()){
                ovChipkaart = (parseStatement(resultSet));
            }

            resultSet.close();
            return ovChipkaart;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger r) throws SQLException {
        ArrayList<OVChipkaart> ovChipkaarten = new ArrayList<OVChipkaart>();
        try{
            PreparedStatement preparedStatement =  conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?;");
            preparedStatement.setInt(1, r.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet != null && resultSet.next()){
                ovChipkaarten.add(parseStatement(resultSet));
            }

            resultSet.close();
            return ovChipkaarten;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    private OVChipkaart parseStatement(ResultSet theSet) throws SQLException{
        OVChipkaart o = new OVChipkaart();

        o.setKaart_nummer(theSet.getInt("kaart_nummer"));
        o.setSaldo(theSet.getDouble("saldo"));
        o.setGeldig_tot(theSet.getDate("geldig_tot"));
        o.setKlasse(theSet.getInt("klasse"));

        return o;
    }
}
