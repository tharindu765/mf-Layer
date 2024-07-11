package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PackageBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PackageDAO;
import lk.ijse.dto.PackageDTO;
import lk.ijse.entity.Package;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageBOImpl implements PackageBO {
PackageDAO packageDao = (PackageDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PACKAGE);
    @Override
    public List<PackageDTO> getAll() throws SQLException, ClassNotFoundException {
        List<Package> all = packageDao.getAll();
        List<PackageDTO> l= new ArrayList<>();
        for (Package p : all){
            l.add(new PackageDTO(p.getPackageID(),p.getDescription(),p.getUnitPrice(),p.getQuantity(),p.getBatchID()));
        }
        return l;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
    return packageDao.getCurrentId();
    }

    @Override
    public boolean addPackage(String description, double unitPrice, int quantity, String batchID, int packageID) throws SQLException, ClassNotFoundException {
    return packageDao.addPackage(description, unitPrice,  quantity,  batchID,  packageID);
    }
    @Override
    public List<PackageDTO> searchPackagesByBatchID(String batchID) throws SQLException {
        List<Package> all =  packageDao.searchPackagesByBatchID(batchID);
        List<PackageDTO> l= new ArrayList<>();
        for (Package p : all){
            l.add(new PackageDTO(p.getPackageID(),p.getDescription(),p.getUnitPrice(),p.getQuantity(),p.getBatchID()));
        }
        return l;
    }
    @Override
    public boolean updatePackage(int packageID, String description, double unitPrice, int quantity, String batchID) throws SQLException {
    return packageDao.updatePackage(packageID,description, unitPrice,  quantity,  batchID  );
    }
    @Override
    public boolean deletePackage(int packageID) throws SQLException {
    return packageDao.deletePackage(packageID);
    }
}