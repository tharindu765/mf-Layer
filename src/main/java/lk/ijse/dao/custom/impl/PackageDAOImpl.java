package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.PackageDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.Package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageDAOImpl implements PackageDAO {
    @Override
    public List<Package> getAll() throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM PackageDetails";
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            ResultSet resultSet = preparedStatement.executeQuery();
            List<Package> packageList = new ArrayList<>();

            while (resultSet.next()) {
                int packageID = resultSet.getInt("PackageID");
                String description = resultSet.getString("Description");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int quantity = resultSet.getInt("Quantity");
                int batchID = resultSet.getInt("BatchID");

                Package package =new Package(packageID, description, unitPrice, quantity, batchID);
                packageList.add(package);
            }

            return packageList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        */
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM PackageDetails");
        List<Package> packageList = new ArrayList<>();

        while (resultSet.next()) {
            int packageID = resultSet.getInt("PackageID");
            String description = resultSet.getString("Description");
            double unitPrice = resultSet.getDouble("UnitPrice");
            int quantity = resultSet.getInt("Quantity");
            int batchID = resultSet.getInt("BatchID");

            Package aPackage = new Package(packageID, description, unitPrice, quantity, batchID);
            packageList.add(aPackage);
        }

        return packageList;
    }

    @Override
    public boolean add(Package entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Package entity) throws SQLException, ClassNotFoundException {
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
    public Package search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
    /*    String sql = "SELECT MAX(PackageID) AS MaxID FROM IncensePackage";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

     */
        ResultSet resultSet = SQLUtil.execute("SELECT MAX(PackageID) AS MaxID FROM IncensePackage");
        if (resultSet.next()) {
            String PackageId = resultSet.getString(1);
            return PackageId;
        }
        return null;
    }
    @Override
    public boolean addPackage(String description, double unitPrice, int quantity, String batchID, int packageID) throws SQLException, ClassNotFoundException {

        /*String sql1 = "INSERT INTO IncensePackage (PackageID, Description, UnitPrice, Quantity) VALUES (?, ?, ?, ?)";
        String sql2 = "INSERT INTO PackageDetail (PackageID, BatchID) VALUES (?, ?)";
*/
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
/*
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, packageID);
            preparedStatement1.setString(2, description);
            preparedStatement1.setDouble(3, unitPrice);
            preparedStatement1.setInt(4, quantity);

            int affectedRows1 = preparedStatement1.executeUpdate();
*/try
        {
        boolean resultSet = SQLUtil.execute("INSERT INTO IncensePackage (PackageID, Description, UnitPrice, Quantity) VALUES (?, ?, ?, ?)", packageID, description, unitPrice, quantity);
        if(resultSet){
           boolean affectRows = SQLUtil.execute("INSERT INTO PackageDetail (PackageID, BatchID) VALUES (?, ?)",packageID,Integer.parseInt(batchID));
           if (affectRows){
               connection.commit();
               return true;
           }
        }
        connection.rollback();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        connection.setAutoCommit(true);
    }
        return false;
        /*
            if (affectedRows1 > 0) {
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, packageID);
                preparedStatement2.setInt(2, Integer.parseInt(batchID));

                int affectedRows2 = preparedStatement2.executeUpdate();

                if (affectedRows2 > 0) {
                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }

        return false;

         */
    }
@Override
    public List<Package> searchPackagesByBatchID(String batchID) throws SQLException {
       /* String sql = "SELECT * FROM PackageDetails WHERE BatchID = ?";
        Connection connection = DbConnection.getInstance().getConnection();

        */
        List<Package> packageList = new ArrayList<>();
        //PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            ResultSet resultSet = SQLUtil.execute("SELECT * FROM PackageDetails WHERE BatchID = ?",batchID);

            while (resultSet.next()) {
                int packageID = resultSet.getInt("PackageID");
                String description = resultSet.getString("Description");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int quantity = resultSet.getInt("Quantity");

                Package apackage = new Package(packageID, description, unitPrice, quantity, Integer.parseInt(batchID));
                packageList.add(apackage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    return packageList;
    }
    @Override
    public boolean updatePackage(int packageID, String description, double unitPrice, int quantity, String batchID) throws SQLException {
     /*   String sql = "UPDATE IncensePackage SET Description=?, UnitPrice=?, Quantity=? WHERE PackageID=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

      */
        try {
            boolean affectedRows = SQLUtil.execute("UPDATE IncensePackage SET Description=?, UnitPrice=?, Quantity=? WHERE PackageID=?",description,unitPrice,quantity,packageID);
            if (affectedRows) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    @Override
    public boolean deletePackage(int packageID) throws SQLException {
        /*String sql = "DELETE FROM IncensePackage WHERE PackageID=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

         */
        try {

           boolean affectedRows = SQLUtil.execute("DELETE FROM IncensePackage WHERE PackageID=?",packageID);

            if (affectedRows) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

}