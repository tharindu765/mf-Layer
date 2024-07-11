package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PackageDTO;
import lk.ijse.entity.Package;

import java.sql.SQLException;
import java.util.List;

public interface PackageBO extends SuperBO {
    List<PackageDTO> getAll() throws SQLException, ClassNotFoundException;

    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean addPackage(String description, double unitPrice, int quantity, String batchID, int packageID) throws SQLException, ClassNotFoundException;

    List<PackageDTO> searchPackagesByBatchID(String batchID) throws SQLException;

    boolean updatePackage(int packageID, String description, double unitPrice, int quantity, String batchID) throws SQLException;

    boolean deletePackage(int packageID) throws SQLException;
}
