package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.IncensePackageDAO;
import lk.ijse.entity.IncensePackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncensePackageDAOImpl implements IncensePackageDAO {

    @Override
    public boolean exitPackage(String incenseType) throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT * FROM IncensePackage WHERE Description = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, incenseType); // Set the parameter value for the Description column
        ResultSet resultSet = pstm.executeQuery();
        return resultSet.next(); // Return true if a record is found, false otherwise
        */
        return SQLUtil.execute("SELECT * FROM IncensePackage WHERE Description = ?",incenseType);
    }

    @Override
    public List<String> getIncenseType() throws SQLException, ClassNotFoundException {
        /*String sql = "select Description from IncensePackage";
        PreparedStatement pstm =DbConnection.getInstance().getConnection().prepareStatement(sql);
         */
        ResultSet resultSet = SQLUtil.execute("SELECT Description from IncensePackage");
        List<String> incenseTypeList = new ArrayList<>();
        while (resultSet.next()){
            String incenseType = resultSet.getString(1);
            incenseTypeList.add(incenseType);
        }
        return incenseTypeList;
    }
    @Override
    public int getPackageId(String incenseType) throws SQLException, ClassNotFoundException {
       /* String sql = "SELECT PackageID FROM IncensePackage WHERE Description = ?";
           PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        */
            ResultSet rst =SQLUtil.execute("SELECT PackageID FROM IncensePackage WHERE Description = ?",incenseType);

            if (rst.next()) {
                return rst.getInt("PackageID");
            }
        return -1;
    }

    @Override
    public List<IncensePackage> getAll() throws SQLException, ClassNotFoundException {
        /*String sql = "SELECT * FROM IncensePackage";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);

         */
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM IncensePackage");

        List<IncensePackage> packageList = new ArrayList<>();
        while (resultSet.next()) {
            int packageId = resultSet.getInt("PackageID");
            String description = resultSet.getString("Description");
            int quantity = resultSet.getInt("Quantity");

            IncensePackage incensePackage = new IncensePackage(packageId, description, quantity);
            packageList.add(incensePackage);
        }
        return packageList;
    }

    @Override
    public boolean add(IncensePackage entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(IncensePackage entity) throws SQLException, ClassNotFoundException {
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
    public IncensePackage search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

}
