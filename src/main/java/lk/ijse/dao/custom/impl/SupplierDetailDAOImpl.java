package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDetailDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.SupplierDetail;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SupplierDetailDAOImpl implements SupplierDetailDAO {
    @Override
    public boolean save(List<SupplierDetail> supplierDetails) throws SQLException, ClassNotFoundException {
        for (SupplierDetail sd : supplierDetails) {
            boolean isSaved = save(sd.getRawMaterialId(),sd.getSupplierName(),sd.getDate(),sd.getUnitPrice());
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean save(int rawMaterialId, String supplierName, Date date, double unitPrice) throws SQLException, ClassNotFoundException {
      /*  String insertSupplierDetailSql = "INSERT INTO SupplierDetail (RawMaterialID, SupplierID, Date, Price) VALUES (?, (SELECT SupplierID FROM Supplier WHERE Name = ?), ?, ?)";
        PreparedStatement preparedStatement1 = DBConnection.getInstance().getConnection().prepareStatement(insertSupplierDetailSql);
            preparedStatement1.setInt(1, rawMaterialId);
            preparedStatement1.setString(2, supplierName);
            preparedStatement1.setDate(3, date);
            preparedStatement1.setDouble(4, unitPrice);
            return preparedStatement1.executeUpdate() > 0;
       */
        return SQLUtil.execute("INSERT INTO SupplierDetail (RawMaterialID, SupplierID, Date, Price) VALUES (?, (SELECT SupplierID FROM Supplier WHERE Name = ?), ?, ?)",rawMaterialId,supplierName,date,unitPrice);
    }
}

