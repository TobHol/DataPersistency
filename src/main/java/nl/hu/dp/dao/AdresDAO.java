package nl.hu.dp.dao;
import nl.hu.dp.domains.Adres;
import nl.hu.dp.domains.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    public boolean save(Adres adres)throws SQLException;
    public boolean update(Adres adres)throws SQLException;
    public boolean delete(Adres adres)throws SQLException;

    public List<Adres> findAll()throws SQLException;
    public Adres findById(int id)throws SQLException;
    public Adres findByReiziger(Reiziger reziger)throws SQLException;
}
