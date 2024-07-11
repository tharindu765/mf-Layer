package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.IncensePackage;

import java.sql.SQLException;
import java.util.List;

public interface IncensePackageDAO extends CrudDAO<IncensePackage> {
    boolean exitPackage(String incenseType) throws SQLException, ClassNotFoundException;

    List<String> getIncenseType() throws SQLException, ClassNotFoundException;

    int getPackageId(String incenseType) throws SQLException, ClassNotFoundException;
}
