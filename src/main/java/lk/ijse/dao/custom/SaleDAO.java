package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Sale;

import java.sql.SQLException;
import java.util.List;

public interface SaleDAO extends CrudDAO<Sale> {
    String getCurrentId() throws SQLException, ClassNotFoundException;

    List<Sale> searchSalesByCustomerID(String customerID) throws SQLException, ClassNotFoundException;

    String getStatusByOrderIDAndCustomerID(String orderID, String customerID) throws SQLException, ClassNotFoundException;

    boolean updateStatusByOrderIDAndCustomerID(String orderID, String newStatus) throws SQLException, ClassNotFoundException;

    boolean updateSaleQuantity(int orderID, int qty) throws SQLException, ClassNotFoundException;

    String getSaleStatusByOrderID(String orderID) throws SQLException, ClassNotFoundException;

    List<Sale> getAllSalesByPaymentStatus() throws SQLException, ClassNotFoundException;
}
