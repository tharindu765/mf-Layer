package lk.ijse.dao.custom.impl;


import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.tdm.OrderTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean save(List<OrderDetail> odList) throws SQLException, ClassNotFoundException {
        for (OrderDetail od : odList) {
            boolean isSaved = Save(od.getPackageID(), od.getOrderID(), od.getTotalPrice(), od.getQty());
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean Save(int packageID, int orderID, double totalPrice, int qty) throws SQLException, ClassNotFoundException {
       /* String sql = "INSERT INTO OrderDetail (PackageID, OrderID, Price, Quantity) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setInt(1, packageID);
        pst.setInt(2, orderID);
        pst.setDouble(3, totalPrice);
        pst.setInt(4, qty);
        return pst.executeUpdate() > 0;
        */
        return SQLUtil.execute("INSERT INTO OrderDetail (PackageID, OrderID, Price, Quantity) VALUES (?, ?, ?, ?)",packageID,orderID,totalPrice,qty);
    }


    @Override
    public boolean updateOrderDetail(OrderTm selectedOrder, int packageId) throws SQLException, ClassNotFoundException {
        /*String sql = "UPDATE OrderDetail SET Price = ?, Quantity = ? WHERE OrderID = ? AND PackageID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setDouble(1, selectedOrder.getTotalPrice());
        pst.setInt(2, selectedOrder.getQty());
        pst.setInt(3, selectedOrder.getOrderID());
        pst.setInt(4, packageId);
        return pst.executeUpdate() > 0;
         */
        return SQLUtil.execute("UPDATE OrderDetail SET Price = ?, Quantity = ? WHERE OrderID = ? AND PackageID = ?",selectedOrder.getTotalPrice(),selectedOrder.getQty(),selectedOrder.getOrderID(),packageId);
    }

    @Override
    public double calculateTotalPrice(int orderID) throws SQLException, ClassNotFoundException {
        List<OrderDetail> orderDetails = getOrderDetailsByOrderID(orderID);
        double totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getTotalPrice() * orderDetail.getQty();
        }
        return totalPrice;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderID(int orderID) throws SQLException, ClassNotFoundException {
        /*List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetail WHERE OrderID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setInt(1, orderID);
         */
        List<OrderDetail> orderDetails = new ArrayList<>();
        ResultSet rs = SQLUtil.execute("SELECT * FROM OrderDetail WHERE OrderID = ?",orderID);
        while (rs.next()) {
            int packageID = rs.getInt("PackageID");
            double price = rs.getDouble("Price");
            int qty = rs.getInt("Quantity");
            OrderDetail orderDetail = new OrderDetail(packageID, orderID, price, qty);
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
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
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
