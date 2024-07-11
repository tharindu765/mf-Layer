package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.db.DBConnection;

import lk.ijse.entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public List<String> getSupplierName() throws SQLException, SQLException, ClassNotFoundException {
     /*   String sql = "SELECT Name FROM Supplier";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<String> supplierNames = new ArrayList<>();

        while (resultSet.next()) {
            String supplierName = resultSet.getString("Name");
            supplierNames.add(supplierName);
        }

        return supplierNames;
      */
        ResultSet resultSet = SQLUtil.execute("SELECT Name FROM Supplier");
        List<String> supplierNames = new ArrayList<>();

        while (resultSet.next()) {
            String supplierName = resultSet.getString("Name");
            supplierNames.add(supplierName);
        }
return supplierNames;
    }

    @Override
    public List<Supplier> getAll() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM Supplier";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Supplier> suppliers = new ArrayList<>();

        while (resultSet.next()) {
            String supplierID = resultSet.getString("SupplierID");
            String name = resultSet.getString("Name");
            String telNumber = resultSet.getString("TelNumber");

            Supplier supplier = new Supplier(supplierID, name, telNumber);
            suppliers.add(supplier);
        }

        return suppliers;
         */
        ArrayList<Supplier> allSupplier = new ArrayList<>();
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Supplier");
        while (resultSet.next()) {
            Supplier supplier = new Supplier(resultSet.getString("SupplierID"), resultSet.getString("Name"), resultSet.getString("TelNumber"));
            allSupplier.add(supplier);
        }
        return allSupplier;
    }
    @Override
    public boolean add(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Supplier (Name, TelNumber,SupplierID) VALUES (?, ?,?)", entity.getName(), entity.getTel(), entity.getSupplierID());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Supplier SET Name = ?, TelNumber = ? WHERE SupplierID = ?", entity.getName(), entity.getTel(), entity.getSupplierID());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    /*
        @Override
        public boolean add(String name, String tel, String NIC) throws SQLException {
            String sql = "INSERT INTO Supplier (Name, TelNumber,SupplierID) VALUES (?, ?,?)";
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, tel);
            pst.setObject(3, NIC);

           return pst.executeUpdate() > 0;
        }

     */
    @Override
    public Supplier search(String keyword) throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM Supplier WHERE SupplierID = ?";
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, keyword);

        ResultSet resultSet = pst.executeQuery();

        if (resultSet.next()) {
            String supplierID = resultSet.getString("SupplierID");
            String name = resultSet.getString("Name");
            String telNumber = resultSet.getString("TelNumber");

            return new Supplier(supplierID, name, telNumber);
        }

        return null;

        */
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM Supplier WHERE SupplierID = ?", keyword);
        resultSet.next();
        return new Supplier(resultSet.getString("SupplierID"), resultSet.getString("Name"), resultSet.getString("TelNumber"));
    }

    /*
        @Override
        public boolean update(String supplierID, String name, String tel, String nic) throws SQLException {
            String sql = "UPDATE Supplier SET Name = ?, TelNumber = ?, SupplierID = ? WHERE SupplierID = ?";
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, tel);
            pst.setString(3, String.valueOf(nic));
            pst.setString(4, String.valueOf(supplierID));

            return pst.executeUpdate()>0;
        }

     */
    @Override
    public boolean delete(String supplierID) throws SQLException, ClassNotFoundException {
       /* String sql = "DELETE FROM Supplier WHERE SupplierID = ?";
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, String.valueOf(supplierID));
        return pst.executeUpdate() > 0;

        */
        return SQLUtil.execute("DELETE FROM Supplier WHERE SupplierID = ?", supplierID);
    }

    @Override
    public int getTotalSupplierCount() throws SQLException, ClassNotFoundException {
            /*Connection connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT COUNT(*) AS TotalSuppliers FROM Supplier";

             */

                try{
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) AS TotalSuppliers FROM Supplier");
                    if (resultSet.next()) {
                        return resultSet.getInt("TotalSuppliers");
                    }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return 0;
    }
}