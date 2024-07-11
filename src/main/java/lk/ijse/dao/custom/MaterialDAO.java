package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Material;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface MaterialDAO extends CrudDAO<Material> {
    String getCurrentId() throws SQLException, ClassNotFoundException;

    void update(int rawMaterialId, String materialName, int qty, String supplierName, Date date, double price) throws SQLException;

    int getRawMaterialID(String name) throws SQLException, ClassNotFoundException;

    List<Material> searchAllName(String supplierName) throws SQLException, ClassNotFoundException;
}
