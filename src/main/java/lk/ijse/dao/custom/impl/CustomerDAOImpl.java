package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM Customer";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Customer> customerList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(3);
            String address = resultSet.getString(2);
            String tel = resultSet.getString(4);

            Customer customer = new Customer(id, name, address, tel);
            customerList.add(customer);
        }
        return customerList;

         */
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString(1), rst.getString(3), rst.getString(2),rst.getString(4));
            allCustomers.add(customer);
        }
        return allCustomers;
    }
    @Override
    public boolean add(Customer customer) throws SQLException, ClassNotFoundException {
    /*    String sql = "insert into Customer values(?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCustomerID());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getTelNumber());
        pstm.setObject(4, customer.getAddress());
        return pstm.executeUpdate() > 0;

     */
        return SQLUtil.execute("INSERT into Customer values(?,?,?,?)",customer.getCustomerID(),customer.getName(),customer.getTelNumber(),customer.getAddress());
    }
    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        /*String sql = "delete from Customer where CustomerID = ?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);
        return pstm.executeUpdate() > 0;

         */
        return SQLUtil.execute("DELETE from Customer where CustomerID = ?",id);
    }
    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        /*String sql = "update Customer set CustomerID = ?,Name = ?, TelNumber = ?,Address = ? ";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, customer.getCustomerID());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getTelNumber());
        pstm.setObject(4, customer.getAddress());
        return pstm.executeUpdate() > 0;
         */
        return SQLUtil.execute("UPDATE Customer set Name = ?, TelNumber = ?,Address = ? where CustomerID =?",customer.getName(),customer.getTelNumber(),customer.getAddress(),customer.getCustomerID());
    }
    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
       /* String sql = "Select * from Customer where CustomerID = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String Cid = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String address = resultSet.getString(4);
            Customer customer = new Customer(Cid, name, tel, address);
            return customer;
        }
        return null;

        */
        ResultSet resultSet = SQLUtil.execute("SELECT * from Customer where CustomerID = ?", id);
        resultSet.next();
        return new Customer(id,resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
    }

    @Override
    public boolean exist(String name) throws SQLException, ClassNotFoundException {
    /*    String sql = "SELECT CustomerID FROM Customer WHERE Name = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet resultSet = pstm.executeQuery();
        return resultSet.next();

     */
        ResultSet resultSet = SQLUtil.execute("SELECT CustomerID FROM Customer WHERE Name = ?",name);
        return resultSet.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getCustomerID(String name) throws SQLException, ClassNotFoundException {
        /*String sql = "select CustomerID from Customer where Name = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, name);

         */
        ResultSet resultSet = SQLUtil.execute("SELECT CustomerID from Customer where Name = ?",name);
        resultSet.next();
        String Id = resultSet.getString(1);
        return Id;


    }

    @Override
    public List<String> getCustomerID() throws SQLException, ClassNotFoundException {
     /*   String sql = "select CustomerID from Customer";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        List<String> IDList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String ID = String.valueOf(resultSet.getString(1));
            IDList.add(ID);
        }
        return IDList;

      */
        return SQLUtil.execute("SELECT CustomerID from Customer");
    }

    @Override
    public String setCustomerName(String id) throws SQLException, ClassNotFoundException {
        /*String sql = "select Name from Customer where CustomerID = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);

         */
        ResultSet resultSet = SQLUtil.execute("SELECT Name from Customer where CustomerID = ?",id);
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }

    }

    @Override
    public String getCustomerID(int orderID) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT CustomerID FROM Orders WHERE OrderID = ?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setInt(1, orderID);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int customerID = resultSet.getInt("CustomerID");
            return String.valueOf(customerID);
        } else {
            return null;
        }
         */
        return SQLUtil.execute("SELECT CustomerID FROM Orders WHERE OrderID = ?",orderID);
    }
    @Override
    public int getCustomerCount() throws SQLException, ClassNotFoundException {
      /*  String sql = "SELECT COUNT(*) AS CustomerCount FROM Customer";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

       */
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS CustomerCount FROM Customer");
        if (resultSet.next()) {
            return resultSet.getInt("CustomerCount");
        }
        return 0;

    }

 }