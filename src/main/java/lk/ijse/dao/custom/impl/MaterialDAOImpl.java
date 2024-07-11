package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.MaterialDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAOImpl implements MaterialDAO {
    @Override
    public List<Material> getAll() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM MaterialManagementView";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Material> materialList = new ArrayList<>();

        while (resultSet.next()) {
            String materialName = resultSet.getString(2);

            int qty = resultSet.getInt(3);
            String supplierName = resultSet.getString(5);

            Date date = resultSet.getDate(7);
            double price = resultSet.getDouble(8);

            Material material = new Material(materialName, qty, supplierName, date, price);
            materialList.add(material);
        }
        return materialList;
         */
        ResultSet resultSet= SQLUtil.execute("SELECT * FROM MaterialManagementView");
        List<Material> materialList = new ArrayList<>();

        while (resultSet.next()) {
            Material material = new Material(resultSet.getString(2),resultSet.getInt(3),resultSet.getString(5),resultSet.getDate(7),resultSet.getDouble(8));
            materialList.add(material);
        }
return materialList;
    }
    @Override
    public  String getCurrentId() throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT MAX(RawMaterialID) FROM RawMaterial";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
        */
        return SQLUtil.execute("SELECT MAX(RawMaterialID) FROM RawMaterial");
    }
    @Override
    public boolean add(Material rawMaterial) throws SQLException, ClassNotFoundException {
        /*String insertRawMaterialSql = "INSERT INTO RawMaterial (RawMaterialID, Name, Quantity) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(insertRawMaterialSql);
            preparedStatement.setObject(1, rawMaterial.getRawMaterialId());
            preparedStatement.setObject(2, rawMaterial.getMaterialName());
            preparedStatement.setObject(3, rawMaterial.getQty());
            return preparedStatement.executeUpdate() > 0;
         */
     return SQLUtil.execute("INSERT INTO RawMaterial (RawMaterialID, Name, Quantity) VALUES (?, ?, ?)", rawMaterial.getRawMaterialId(), rawMaterial.getMaterialName(), rawMaterial.getQty());
    }

    @Override
    public boolean update(Material entity) throws SQLException, ClassNotFoundException {
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
    public Material search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public void update(int rawMaterialId, String materialName, int qty, String supplierName, Date date, double price) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            // Update RawMaterial table
            boolean b = updateRawMaterial(rawMaterialId, materialName, qty, supplierName, date, price);
            if (b){
                boolean b1 = updateSupplierDetail(rawMaterialId, materialName, qty, supplierName, date, price);
                if (b1){
                    connection.commit();
                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
    public boolean updateSupplierDetail(int rawMaterialId, String materialName, int qty, String supplierName, Date date, double price) throws SQLException, ClassNotFoundException {
        /*String updateSupplierDetailSql = "UPDATE SupplierDetail SET Date=?, Price=? WHERE RawMaterialID=?";
        PreparedStatement supplierDetailStatement = connection.prepareStatement(updateSupplierDetailSql);
        supplierDetailStatement.setObject(1, date);
        supplierDetailStatement.setObject(2, price);
        supplierDetailStatement.setObject(3, rawMaterialId);
        supplierDetailStatement.executeUpdate();
         */
       return SQLUtil.execute("UPDATE SupplierDetail SET Date=?, Price=? WHERE RawMaterialID=?",date,price,rawMaterialId);
    }
    //@Override
    public boolean updateRawMaterial(int rawMaterialId, String materialName, int qty, String supplierName, Date date, double price) throws SQLException, ClassNotFoundException {
       /* String updateRawMaterialSql = "UPDATE RawMaterial SET Name=?, Quantity=? WHERE RawMaterialID=?";
        PreparedStatement rawMaterialStatement = connection.prepareStatement(updateRawMaterialSql);
        rawMaterialStatement.setObject(1, materialName);
        rawMaterialStatement.setObject(2, qty);
        rawMaterialStatement.setObject(3, rawMaterialId);
        rawMaterialStatement.executeUpdate();
        */
        return SQLUtil.execute("UPDATE RawMaterial SET Name=?, Quantity=? WHERE RawMaterialID=?",materialName,qty,rawMaterialId);
    }
    @Override
    public int getRawMaterialID(String name) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT RawMaterialID FROM RawMaterial WHERE Name=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
      
         */
        ResultSet resultSet = SQLUtil.execute("SELECT RawMaterialID FROM RawMaterial WHERE Name=?",name);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    @Override
    public List<Material> searchAllName(String supplierName) throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM MaterialManagementView WHERE SupplierName LIKE ?";
           PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1,supplierName);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Material> materialList = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int qty = resultSet.getInt(3);
                String supplier = resultSet.getString(5);
                Date date = resultSet.getDate(6);
                double price = resultSet.getDouble(7);

                Material material = new Material(name, qty, supplier, date, price);
                materialList.add(material);
            }

            return materialList;

         */
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM MaterialManagementView WHERE SupplierName LIKE ?",supplierName);
        List<Material> materialList = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString(2);
            int qty = resultSet.getInt(3);
            String supplier = resultSet.getString(5);
            Date date = resultSet.getDate(6);
            double price = resultSet.getDouble(7);

            Material material = new Material(name, qty, supplier, date, price);
            materialList.add(material);
        }

        return materialList;

    }
}

