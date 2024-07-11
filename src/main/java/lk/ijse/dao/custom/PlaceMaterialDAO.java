package lk.ijse.dao.custom;

import lk.ijse.entity.PlaceMaterial;

import java.sql.SQLException;

public interface PlaceMaterialDAO {
    boolean placeMaterial(PlaceMaterial pm) throws SQLException;
}
