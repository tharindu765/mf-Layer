package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SaleDAO;
import lk.ijse.entity.Sale;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleDAOImpl implements SaleDAO{
    @Override
    public List<Sale> getAll() throws SQLException, ClassNotFoundException {
        /*String sql = "select * from SaleDetails";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

         */
        ResultSet resultSet = SQLUtil.execute("SELECT * from SaleDetails");
        List<Sale> saleList = new ArrayList<>();
        while (resultSet.next()){
            int transationID = resultSet.getInt(1);
            int qty = resultSet.getInt(2);
            Date sDate = resultSet.getDate(3);
            int orderID = resultSet.getInt(4);
            Date oDate =resultSet.getDate(5);
            String customerName = resultSet.getString(7);
            String status = resultSet.getString(8);
            String pType = resultSet.getString(9);
            Sale sale = new Sale(transationID,qty,sDate,orderID,oDate,customerName,status,pType);
            saleList.add(sale);
        }
        return saleList;

    }
    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT TransactionID FROM Sale ORDER BY OrderID DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        */
        ResultSet resultSet =SQLUtil.execute("SELECT TransactionID FROM Sale ORDER BY OrderID DESC LIMIT 1");
        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;

    }

    @Override
    public boolean add(Sale sale) throws SQLException, ClassNotFoundException {
        /*String sql = "INSERT INTO Sale values(?,?,?,?,?)";
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, String.valueOf(sale.getTransactionID()));
        pst.setString(2, String.valueOf(sale.getQty()));

        pst.setString(3, String.valueOf(now));
        pst.setObject(4,sale.getOrderID());
        pst.setObject(5,"Pending");
        return pst.executeUpdate()> 0;
         */
        LocalDate now = LocalDate.now();
        return SQLUtil.execute("INSERT INTO Sale values(?,?,?,?,?)",sale.getTransactionID(),sale.getQty(),now,sale.getOrderID(),"Pending");
    }

    @Override
    public boolean update(Sale entity) throws SQLException, ClassNotFoundException {
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
    public Sale search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Sale> searchSalesByCustomerID(String customerID) throws SQLException, ClassNotFoundException {
      /*  String sql = "SELECT * FROM SaleDetails WHERE customerID = ?";
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, customerID);
 */
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM SaleDetails WHERE customerID = ?",customerID);
        List<Sale> saleList = new ArrayList<>();
        while (resultSet.next()) {
            int transationID = resultSet.getInt(1);
            int qty = resultSet.getInt(2);
            Date sDate = resultSet.getDate(3);
            int orderID = resultSet.getInt(4);
            Date oDate = resultSet.getDate(5);
            String customerName = resultSet.getString(7);
            String status = resultSet.getString(8);
            String pType = resultSet.getString(9);

            Sale sale = new Sale(transationID, qty, sDate, orderID, oDate, customerName, status, pType);
            saleList.add(sale);
        }
        return saleList;
    }
    @Override
    public String getStatusByOrderIDAndCustomerID(String orderID, String customerID) throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT PaymentStatus FROM SaleDetails WHERE OrderID = ? AND CustomerID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, orderID);
        pst.setString(2, customerID);

        */

        ResultSet resultSet = SQLUtil.execute("SELECT PaymentStatus FROM SaleDetails WHERE OrderID = ? AND CustomerID = ?",orderID,customerID);
        if (resultSet.next()) {
            return resultSet.getString("PaymentStatus");
        } else {
            return null;
        }
    }
    @Override
    public boolean updateStatusByOrderIDAndCustomerID(String orderID, String newStatus) throws SQLException, ClassNotFoundException {
    /*    String sql = "UPDATE Sale SET PaymentStatus = ? WHERE OrderID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, newStatus);
        pst.setString(2, orderID);

        return pst.executeUpdate() > 0;

     */
        return SQLUtil.execute("UPDATE Sale SET PaymentStatus = ? WHERE OrderID = ?",newStatus,orderID);
    }
    @Override
    public boolean updateSaleQuantity(int orderID, int qty) throws SQLException, ClassNotFoundException {
      /*  String sql = "UPDATE Sale SET Quantity = ? WHERE OrderID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setInt(1, qty);
        pst.setInt(2, orderID);

        return pst.executeUpdate() > 0;

       */
        return SQLUtil.execute("UPDATE Sale SET Quantity = ? WHERE OrderID = ?",qty,orderID);
    }
    @Override
    public String getSaleStatusByOrderID(String orderID) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT PaymentStatus FROM Sale WHERE OrderID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, orderID);

         */
        ResultSet resultSet = SQLUtil.execute("SELECT PaymentStatus FROM Sale WHERE OrderID = ?");
        if (resultSet.next()) {
            return resultSet.getString("PaymentStatus");
        } else {
            return null;
        }
    }
    @Override
    public List<Sale> getAllSalesByPaymentStatus() throws SQLException, ClassNotFoundException {
        List<Sale> sales = new ArrayList<>();
        /*String sql = "SELECT PaymentStatus, COUNT(*) AS Count FROM Sale GROUP BY PaymentStatus";
        Connection connection = DbConnection.getInstance().getConnection();
        try {
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String paymentStatus = resultSet.getString("PaymentStatus");
                int count = resultSet.getInt("Count");
                sales.add(new Sale(paymentStatus, count));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sales;

         */
        ResultSet resultSet = SQLUtil.execute("SELECT PaymentStatus, COUNT(*) AS Count FROM Sale GROUP BY PaymentStatus");
        while (resultSet.next()) {
            String paymentStatus = resultSet.getString("PaymentStatus");
            int count = resultSet.getInt("Count");
            sales.add(new Sale(paymentStatus, count));
        }
            return sales;
    }
}

