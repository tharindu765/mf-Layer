package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SaleBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SaleDAO;
import lk.ijse.dto.SaleDTO;
import lk.ijse.entity.Sale;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleBOImpl implements SaleBO {
    SaleDAO saleDAO = (SaleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALE);
    @Override
    public List<SaleDTO> getAll() throws SQLException, ClassNotFoundException {
       List<Sale> saleList = saleDAO.getAll();
       List<SaleDTO> saleDTOList = new ArrayList<>();
       for (Sale s : saleList){
           SaleDTO saleDTO = new SaleDTO(s.getTransactionID(), s.getQty(), s.getSDate(), s.getOrderID(), s.getODate(), s.getCustomerName(), s.getStatus(), s.getPType());
           saleDTOList.add(saleDTO);
       }
       return saleDTOList;
    }
    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
       return saleDAO.getCurrentId();
    }

    @Override
    public boolean add(SaleDTO sale) throws SQLException, ClassNotFoundException {
        Sale saleN = new Sale(sale.getTransactionID(),sale.getQty(), sale.getSDate(),sale.getOrderID(),sale.getODate(),sale.getCustomerName(),sale.getStatus(),sale.getPType());
        return saleDAO.add(saleN);
    }

    @Override
    public boolean update(SaleDTO entity) throws SQLException, ClassNotFoundException {
        return false;
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
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SaleDTO search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<SaleDTO> searchSalesByCustomerID(String customerID) throws SQLException, ClassNotFoundException {
        List<Sale> saleList = saleDAO.searchSalesByCustomerID(customerID);
        List<SaleDTO> saleDTOList = new ArrayList<>();
        for (Sale s : saleList){
            SaleDTO saleDTO = new SaleDTO(s.getTransactionID(), s.getQty(), s.getSDate(), s.getOrderID(), s.getODate(), s.getCustomerName(), s.getStatus(), s.getPType());
            saleDTOList.add(saleDTO);
        }
        return saleDTOList;

    }
    @Override
    public String getStatusByOrderIDAndCustomerID(String orderID, String customerID) throws SQLException, ClassNotFoundException {
       return saleDAO.getStatusByOrderIDAndCustomerID(orderID,customerID);
    }
    @Override
    public boolean updateStatusByOrderIDAndCustomerID(String orderID, String newStatus) throws SQLException, ClassNotFoundException {
    return saleDAO.updateStatusByOrderIDAndCustomerID(orderID,newStatus);
    }
    @Override
    public boolean updateSaleQuantity(int orderID, int qty) throws SQLException, ClassNotFoundException {
    return saleDAO.updateSaleQuantity(orderID,qty);
    }
    @Override
    public String getSaleStatusByOrderID(String orderID) throws SQLException, ClassNotFoundException {
        return saleDAO.getSaleStatusByOrderID(orderID);
    }
    @Override
    public List<SaleDTO> getAllSalesByPaymentStatus() throws SQLException, ClassNotFoundException {
        List<Sale> allSalesByPaymentStatus = saleDAO.getAllSalesByPaymentStatus();
        List<SaleDTO> saleDTOList = new ArrayList<>();
        for (Sale s : allSalesByPaymentStatus){
            SaleDTO saleDTO = new SaleDTO(s.getTransactionID(), s.getQty(), s.getSDate(), s.getOrderID(), s.getODate(), s.getCustomerName(), s.getStatus(), s.getPType());
            saleDTOList.add(saleDTO);
        }
        return saleDTOList;

    }
}

