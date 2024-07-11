package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Batch;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface BatchDAO extends CrudDAO<Batch> {
    List<String> getBatchID() throws SQLException;

    List<Batch> searchBatches(String batchIDText, Date date, String qtyText) throws SQLException;
}
