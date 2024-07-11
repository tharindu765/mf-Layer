package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Package;

import java.sql.SQLException;
import java.util.List;

public interface PackageDAO extends CrudDAO<Package> {
    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean addPackage(String description, double unitPrice, int quantity, String batchID, int packageID) throws SQLException, ClassNotFoundException;

    List<Package> searchPackagesByBatchID(String batchID) throws SQLException;

    boolean updatePackage(int packageID, String description, double unitPrice, int quantity, String batchID) throws SQLException;

    boolean deletePackage(int packageID) throws SQLException;
}
