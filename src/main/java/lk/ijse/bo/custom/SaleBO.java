package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SaleDTO;
import lk.ijse.entity.Sale;

import java.sql.SQLException;
import java.util.List;

public interface SaleBO extends SuperBO{
    List<SaleDTO> getAll() throws SQLException, ClassNotFoundException;

    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean add(SaleDTO sale) throws SQLException, ClassNotFoundException;

    boolean update(SaleDTO entity) throws SQLException, ClassNotFoundException;

    boolean exist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    SaleDTO search(String id) throws SQLException, ClassNotFoundException;

    List<SaleDTO> searchSalesByCustomerID(String customerID) throws SQLException, ClassNotFoundException;

    String getStatusByOrderIDAndCustomerID(String orderID, String customerID) throws SQLException, ClassNotFoundException;

    boolean updateStatusByOrderIDAndCustomerID(String orderID, String newStatus) throws SQLException, ClassNotFoundException;

    boolean updateSaleQuantity(int orderID, int qty) throws SQLException, ClassNotFoundException;

    String getSaleStatusByOrderID(String orderID) throws SQLException, ClassNotFoundException;

    List<SaleDTO> getAllSalesByPaymentStatus() throws SQLException, ClassNotFoundException;
}
