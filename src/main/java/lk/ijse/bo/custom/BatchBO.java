package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BatchDTO;
import lk.ijse.entity.Batch;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface BatchBO extends SuperBO {
    List<String> getBatchID() throws SQLException;

    List<BatchDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean add(Batch batch) throws SQLException, ClassNotFoundException;

    boolean update(Batch selectedBatch) throws SQLException, ClassNotFoundException;

    List<BatchDTO> searchBatches(String batchIDText, Date date, String qtyText) throws SQLException;
}
