package lk.ijse.dao.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.SupplierDetail;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface SupplierDetailDAO extends SuperDAO {

    boolean save(List<SupplierDetail> supplierDetails) throws SQLException, ClassNotFoundException;

    boolean save(int rawMaterialId, String supplierName, Date date, double unitPrice) throws SQLException, ClassNotFoundException;
}
