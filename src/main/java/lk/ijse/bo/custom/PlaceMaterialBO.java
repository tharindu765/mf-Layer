package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PlaceMaterialDTO;

import java.sql.SQLException;

public interface PlaceMaterialBO extends SuperBO {
    boolean placeMaterial(PlaceMaterialDTO pm) throws SQLException;
}
