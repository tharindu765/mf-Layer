package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.CrudDAO;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.entity.Material;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface MaterialBO extends SuperBO{
    List<MaterialDTO> getAll() throws SQLException, ClassNotFoundException;

    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean add(Material rawMaterial) throws SQLException, ClassNotFoundException;

    boolean update(Material entity) throws SQLException, ClassNotFoundException;

    boolean exist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    MaterialDTO search(String id) throws SQLException, ClassNotFoundException;

    void update(int rawMaterialId, String materialName, int qty, String supplierName, Date date, double price) throws SQLException;

    int getRawMaterialID(String name) throws SQLException, ClassNotFoundException;

    List<MaterialDTO> searchAllName(String supplierName) throws SQLException, ClassNotFoundException;
}
