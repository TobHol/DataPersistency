package nl.hu.dp.dao;

import java.sql.SQLException;
import java.util.List;

import nl.hu.dp.domains.OVChipkaart;
import nl.hu.dp.domains.Product;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;
    public boolean update(Product product)  throws SQLException;
    public boolean delete(Product product) throws SQLException;

    public List<Product> findAll()  throws SQLException;
    public Product findById(int id)  throws SQLException;
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart)  throws SQLException;
}
