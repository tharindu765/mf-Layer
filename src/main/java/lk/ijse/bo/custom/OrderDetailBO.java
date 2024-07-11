package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailBO extends SuperBO {
    boolean save(List<OrderDetailDTO> odList) throws SQLException, ClassNotFoundException;

    boolean Save(int packageID, int orderID, double totalPrice, int qty) throws SQLException, ClassNotFoundException;

    boolean updateOrderDetail(lk.ijse.tdm.OrderTm selectedOrder, int packageId) throws SQLException, ClassNotFoundException;

    double calculateTotalPrice(int orderID) throws SQLException, ClassNotFoundException;

    List<OrderDetailDTO> getOrderDetailsByOrderID(int orderID) throws SQLException, ClassNotFoundException;

    List<OrderDetailDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean add(OrderDetailDTO entity) throws SQLException, ClassNotFoundException;

    boolean update(OrderDetailDTO entity) throws SQLException, ClassNotFoundException;

    boolean exist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    OrderDetail search(String id) throws SQLException, ClassNotFoundException;
}
