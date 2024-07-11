package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.OrderDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    List<OrderDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean add(OrderDTO entity) throws SQLException, ClassNotFoundException;

    boolean update(OrderDTO entity) throws SQLException, ClassNotFoundException;

    boolean exist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    OrderDTO search(String id) throws SQLException, ClassNotFoundException;

    boolean save(OrderDTO c) throws SQLException, ClassNotFoundException;

    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean addOrder(String orderId, Date date, String customerName) throws SQLException, ClassNotFoundException;

    List<OrderDTO> searchById(String cuID) throws SQLException, ClassNotFoundException;

    List<String> getOrderId() throws SQLException, ClassNotFoundException;

    boolean updateCustomer(int orderID, String newCustomerId) throws SQLException, ClassNotFoundException;

    double calculateNetWorth() throws SQLException, ClassNotFoundException;

    int getOrderCount() throws SQLException, ClassNotFoundException;

    String getCustomerIDFromOrderID(String orderId) throws SQLException, ClassNotFoundException;
}
