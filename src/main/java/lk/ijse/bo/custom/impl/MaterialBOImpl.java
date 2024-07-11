package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.MaterialBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.MaterialDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.dto.MaterialDTO;
import lk.ijse.entity.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialBOImpl implements MaterialBO {
    MaterialDAO materialDAO = (MaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MATERIAL);
    @Override
    public List<MaterialDTO> getAll() throws SQLException, ClassNotFoundException {
        List<MaterialDTO> allmat = new ArrayList<>();
        List<Material> mat = materialDAO.getAll();

        for (Material m : mat){
            allmat.add(new MaterialDTO(m.getMaterialName(),m.getQty(),m.getName(),m.getDate(),m.getUnitPrice(),m.getTotalPrice(),m.getRawMaterialId()));
        }
        return allmat;
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
       ResultSet resultSet = SQLUtil.execute("SELECT MAX(RawMaterialID) FROM RawMaterial");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
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
    public MaterialDTO search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public void update(int rawMaterialId, String materialName, int qty, String supplierName, Date date, double price) throws SQLException {
        /*Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            // Update RawMaterial table
            String updateRawMaterialSql = "UPDATE RawMaterial SET Name=?, Quantity=? WHERE RawMaterialID=?";
            PreparedStatement rawMaterialStatement = connection.prepareStatement(updateRawMaterialSql);
            rawMaterialStatement.setObject(1, materialName);
            rawMaterialStatement.setObject(2, qty);
            rawMaterialStatement.setObject(3, rawMaterialId);
            rawMaterialStatement.executeUpdate();


            String updateSupplierDetailSql = "UPDATE SupplierDetail SET Date=?, Price=? WHERE RawMaterialID=?";
            PreparedStatement supplierDetailStatement = connection.prepareStatement(updateSupplierDetailSql);
            supplierDetailStatement.setObject(1, date);
            supplierDetailStatement.setObject(2, price);
            supplierDetailStatement.setObject(3, rawMaterialId);
            supplierDetailStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }

         */
        materialDAO.update(rawMaterialId, materialName, qty, supplierName, date, price);
    }
    @Override
    public int getRawMaterialID(String name) throws SQLException, ClassNotFoundException {
        /*  String sql = "SELECT RawMaterialID FROM RawMaterial WHERE Name=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;

         */
       return materialDAO.getRawMaterialID(name);
    }

    @Override
    public List<MaterialDTO> searchAllName(String supplierName) throws SQLException, ClassNotFoundException {
          /*  String sql = "SELECT * FROM MaterialManagementView WHERE SupplierName LIKE ?";
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
        List<MaterialDTO> allmat = new ArrayList<>();
        List<Material> mat = materialDAO.searchAllName(supplierName);

        for (Material m : mat){
            allmat.add(new MaterialDTO(m.getMaterialName(),m.getQty(),m.getName(),m.getDate(),m.getUnitPrice(),m.getTotalPrice(),m.getRawMaterialId()));
        }
        return allmat;
        }
}

