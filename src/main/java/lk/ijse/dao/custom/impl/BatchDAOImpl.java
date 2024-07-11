package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.BatchDAO;
import lk.ijse.entity.Batch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchDAOImpl implements BatchDAO {

    @Override
    public List<String> getBatchID() throws SQLException {
        List<String> batchIDs = new ArrayList<>();
        try {
            ResultSet resultSet = SQLUtil.execute("SELECT BatchID FROM Batch");
            while (resultSet.next()) {
                String batchID = resultSet.getString("BatchID");
                batchIDs.add(batchID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return batchIDs;
    }
    @Override
    public List<Batch> getAll() throws SQLException {
        List<Batch> batche = new ArrayList<>();
        try {

            ResultSet resultSet = SQLUtil.execute("select * from BatchView");
            while (resultSet.next()) {
                int batchID = resultSet.getInt("BatchID");
                Date date = resultSet.getDate("Date");
                int qty = resultSet.getInt("TotalQuantity");
                Batch batch = new Batch(batchID, date, qty);
                batche.add(batch);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching batch orders: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return batche;
    }
    @Override
    public boolean add(Batch batch) throws SQLException {
        try {
            int affectedRows = SQLUtil.execute("INSERT INTO Batch (BatchID, Date) VALUES (?, ?)",batch.getBatchID(),batch.getDate());
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving batch: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean update(Batch selectedBatch) throws SQLException {
        String sql = "UPDATE Batch SET Date = ? WHERE BatchID = ?";
        try {
            int affectedRows = SQLUtil.execute("UPDATE Batch SET Date = ? WHERE BatchID = ?",selectedBatch.getDate(),selectedBatch.getBatchID());

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
    public Batch search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Batch> searchBatches(String batchIDText, Date date, String qtyText) throws SQLException {
        String sql = "SELECT * FROM BatchView WHERE Date = ?";
        List<Batch> batches = new ArrayList<>();

        try {
            ResultSet resultSet = SQLUtil.execute("SELECT * FROM BatchView WHERE Date = ?",date);

            while (resultSet.next()) {
                int batchID = resultSet.getInt("BatchID");
                int qty = resultSet.getInt("TotalQuantity");
                Batch batch = new Batch(batchID, date, qty);
                batches.add(batch);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching batches by date: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return batches;
    }
 }
