package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Order;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends CrudDAO<Order> {
    boolean save(Order order) throws SQLException, ClassNotFoundException;

    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean addOrder(String orderId, Date date, String customerName) throws SQLException, ClassNotFoundException;

    List<Order> searchById(String cuID) throws SQLException, ClassNotFoundException;

    List<String> getOrderId() throws SQLException, ClassNotFoundException;

    boolean updateCustomer(int orderID, String newCustomerId) throws SQLException, ClassNotFoundException;

    double calculateNetWorth() throws SQLException, ClassNotFoundException;

    int getOrderCount() throws SQLException, ClassNotFoundException;

    String getCustomerIDFromOrderID(String orderId) throws SQLException, ClassNotFoundException;
}
