package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {
    List<String> getSupplierName() throws SQLException, SQLException, ClassNotFoundException;

    //boolean add(String name, String tel, String NIC) throws SQLException;

    //boolean update(String supplierID, String name, String tel, String nic) throws SQLException;

    int getTotalSupplierCount() throws SQLException, ClassNotFoundException;
}
