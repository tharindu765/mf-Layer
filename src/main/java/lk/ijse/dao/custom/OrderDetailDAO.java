package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    boolean save(List<OrderDetail> odList) throws SQLException, ClassNotFoundException;

    boolean Save(int packageID, int orderID, double totalPrice, int qty) throws SQLException, ClassNotFoundException;

    boolean updateOrderDetail(lk.ijse.tdm.OrderTm selectedOrder, int packageId) throws SQLException, ClassNotFoundException;

    double calculateTotalPrice(int orderID) throws SQLException, ClassNotFoundException;

    List<OrderDetail> getOrderDetailsByOrderID(int orderID) throws SQLException, ClassNotFoundException;
}
