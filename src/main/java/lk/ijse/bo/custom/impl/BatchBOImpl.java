package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BatchBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BatchDAO;
import lk.ijse.dto.BatchDTO;
import lk.ijse.entity.Batch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchBOImpl implements BatchBO {
BatchDAO batchDAO = (BatchDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BATCH);
    @Override
    public List<String> getBatchID() throws SQLException {
        return batchDAO.getBatchID();
    }
    @Override
    public List<BatchDTO> getAll() throws SQLException, ClassNotFoundException {
        List<BatchDTO> batche = new ArrayList<>();
        List<Batch> all = batchDAO.getAll();
        for (Batch b : all){
            batche.add(new BatchDTO(b.getBatchID(),b.getDate(),b.getQty()));
        }
        return batche;
    }
    @Override
    public boolean add(Batch batch) throws SQLException, ClassNotFoundException {
        return batchDAO.add(batch);
    }
    @Override
    public boolean update(Batch selectedBatch) throws SQLException, ClassNotFoundException {
        return batchDAO.update(selectedBatch);
    }
    @Override
    public List<BatchDTO> searchBatches(String batchIDText, Date date, String qtyText) throws SQLException {
        List<BatchDTO> batche = new ArrayList<>();
        List<Batch> all = batchDAO.searchBatches(batchIDText,date,qtyText);
        for (Batch b : all){
            batche.add(new BatchDTO(b.getBatchID(),b.getDate(),b.getQty()));
        }
        return batche;

    }
 }
