package nl.hu.dp.dao;

import nl.hu.dp.domains.Reiziger;

import java.sql.Connection;
import java.sql.RowId;
import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    boolean update(Reiziger inReiziger) throws SQLException;
    boolean save(Reiziger inReiziger) throws SQLException;
    boolean delete(Reiziger inReiziger) throws SQLException;

    List<Reiziger> findAll() throws SQLException;
}
