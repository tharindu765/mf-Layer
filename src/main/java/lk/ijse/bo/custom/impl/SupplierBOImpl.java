package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
   SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public List<String> getSupplierName() throws SQLException, SQLException, ClassNotFoundException {
        return supplierDAO.getSupplierName();
        }

    @Override
    public List<SupplierDTO> getAll() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> allSup = new ArrayList<>();
        List<Supplier> sup = supplierDAO.getAll();
        for(Supplier s:sup){
           allSup.add(new SupplierDTO(s.getSupplierID(),s.getName(),s.getTel()));
        }return allSup;
           }
    @Override
    public boolean add(SupplierDTO entity) throws SQLException, ClassNotFoundException {
        return supplierDAO.add(new Supplier(entity.getSupplierID(),entity.getName(),entity.getTel()));
    }

    @Override
    public boolean update(SupplierDTO entity) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new Supplier(entity.getSupplierID(),entity.getName(),entity.getTel()));
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Supplier search(String keyword) throws SQLException, ClassNotFoundException {
        return supplierDAO.search(keyword);
    }

    @Override
    public boolean delete(String supplierID) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierID);
    }

    @Override
    public int getTotalSupplierCount() throws SQLException, ClassNotFoundException {
           return supplierDAO.getTotalSupplierCount();
    }
}