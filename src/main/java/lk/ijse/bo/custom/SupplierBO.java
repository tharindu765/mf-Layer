package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
    List<String> getSupplierName() throws SQLException, SQLException, ClassNotFoundException;

    List<SupplierDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean add(SupplierDTO entity) throws SQLException, ClassNotFoundException;

    boolean update(SupplierDTO entity) throws SQLException, ClassNotFoundException;


    boolean exist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    Supplier search(String keyword) throws SQLException, ClassNotFoundException;

    boolean delete(String supplierID) throws SQLException, ClassNotFoundException;

    int getTotalSupplierCount() throws SQLException, ClassNotFoundException;
}
