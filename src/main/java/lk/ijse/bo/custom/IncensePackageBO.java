package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.IncensePackageDTO;
import lk.ijse.entity.IncensePackage;

import java.sql.SQLException;
import java.util.List;

public interface IncensePackageBO extends SuperBO {
    boolean exitPackage(String incenseType) throws SQLException, ClassNotFoundException;

    List<String> getIncenseType() throws SQLException, ClassNotFoundException;

    int getPackageId(String incenseType) throws SQLException, ClassNotFoundException;

    List<IncensePackageDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean add(IncensePackage entity) throws SQLException, ClassNotFoundException;

    boolean update(IncensePackage entity) throws SQLException, ClassNotFoundException;

    boolean exist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    IncensePackage search(String id) throws SQLException, ClassNotFoundException;
}
