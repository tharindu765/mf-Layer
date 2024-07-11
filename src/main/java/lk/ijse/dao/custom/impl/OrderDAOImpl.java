package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.entity.Order;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public List<Order> getAll() throws SQLException, ClassNotFoundException {
/*        String sql = "SELECT * FROM OrderDetailView;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

 */
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM OrderDetailView;");
        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            int orderId = resultSet.getInt(1);
            Date date =resultSet.getDate(2);
            String incenseType =resultSet.getString(3);
            String customerName =resultSet.getString(4);
            int qty =resultSet.getInt(5);
            double price=resultSet.getDouble(6);
            Order order =new Order(date,incenseType,customerName,qty,price,orderId);
            orderList.add(order);
        }
        return orderList;
    }

    @Override
    public boolean add(Order entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Order entity) throws SQLException, ClassNotFoundException {
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
    public Order search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Order order) throws SQLException, ClassNotFoundException {
        /*String sql = "INSERT INTO Orders (OrderId, Date, CustomerId) VALUES (?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, order.getOrderId());
       // System.out.println(order.getOrderId());
        //System.out.println( "nothing to see only hateX"+ order.getDate());
       Date dt = order.getDate();
        pstm.setObject(2, dt);
        pstm.setObject(3, order.getCustomerId());
      //  System.out.println(order.getCustomerId());
        return pstm.executeUpdate() > 0;
         */Date dt = order.getDate();
       return SQLUtil.execute("INSERT INTO Orders (OrderId, Date, CustomerId) VALUES (?, ?, ?)",order.getOrderId(),dt,order.getCustomerId());
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
         */
        ResultSet resultSet = SQLUtil.execute("SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1");
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }
    @Override
    public boolean addOrder(String orderId, Date date, String customerName) throws SQLException, ClassNotFoundException {
        /*String sql = "INSERT INTO Orders (OrderID, Date, CustomerID) VALUES (?, ?, (SELECT CustomerID FROM Customer WHERE Name = ?))";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
            pst.setString(1, orderId);
            pst.setDate(2, date);
            pst.setString(3, customerName);
        return pst.executeUpdate() > 0;
         */
        return SQLUtil.execute("INSERT INTO Orders (OrderID, Date, CustomerID) VALUES (?, ?, (SELECT CustomerID FROM Customer WHERE Name = ?))",orderId,date,customerName);
    }
    @Override
    public List<Order> searchById(String cuID) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM OrderDetailView WHERE CustomerName = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, CustomerRepo.setCustomerName(cuID));

         */
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM OrderDetailView WHERE CustomerName = ?",cuID);
        List<Order> orderList = new ArrayList<>();

        while (resultSet.next()) {
            Date date = resultSet.getDate(1);
            String incenseType = resultSet.getString(2);
            String customerName = resultSet.getString(3);
            int qty = resultSet.getInt(4);
            double price = resultSet.getDouble(5);
            Order order = new Order(date, incenseType, customerName, qty, price);
            orderList.add(order);
        }
        return orderList;
    }
    @Override
    public List<String> getOrderId() throws SQLException, ClassNotFoundException {
       /*String sql = "select OrderID from Orders";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        */
        List<String> OBList = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT OrderID from Orders");
        while (resultSet.next()) {
            String OB = String.valueOf(resultSet.getInt(1));
            OBList.add(OB);
        }
        return OBList;
    }
    @Override
    public boolean updateCustomer(int orderID, String newCustomerId) throws SQLException, ClassNotFoundException {
        /*String sql = "UPDATE Orders SET CustomerID = ? WHERE OrderID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, newCustomerId);
        pstm.setInt(2, orderID);
        return pstm.executeUpdate() > 0;
         */
        return SQLUtil.execute("UPDATE Orders SET CustomerID = ? WHERE OrderID = ?",orderID,newCustomerId);
    }
    @Override
    public double calculateNetWorth() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT SUM(od.Price * od.Quantity) AS NetTotal FROM OrderDetail od";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
         */
        ResultSet resultSet = SQLUtil.execute("SELECT SUM(od.Price * od.Quantity) AS NetTotal FROM OrderDetail od");
        if (resultSet.next()) {
            return resultSet.getDouble("NetTotal");
        }
        return 0.0;
    }
    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT COUNT(*) AS OrderCount FROM Orders";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

         */
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS OrderCount FROM Orders");
        if (resultSet.next()) {
            return resultSet.getInt("OrderCount");
        }
        return 0;
    }
    @Override
    public String getCustomerIDFromOrderID(String orderId) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT CustomerID FROM Orders WHERE OrderID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, orderId);
         */
        ResultSet resultSet = SQLUtil.execute("SELECT CustomerID FROM Orders WHERE OrderID = ?",orderId);
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }
}

