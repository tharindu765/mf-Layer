package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PlaceMaterialBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.MaterialDAO;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dao.custom.SupplierDetailDAO;
import lk.ijse.dao.custom.impl.SupplierDetailDAOImpl;
import lk.ijse.db.DBConnection;
import lk.ijse.dto.PlaceMaterialDTO;
import lk.ijse.dto.SupplierDetailDTO;
import lk.ijse.entity.Material;
import lk.ijse.entity.SupplierDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceMaterialBOImpl implements PlaceMaterialBO {
    @Override
    public boolean placeMaterial(PlaceMaterialDTO pm) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        MaterialDAO materialDAO = (MaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MATERIAL);
        SupplierDetailDAO supplierDetailDAO = (SupplierDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIERDETAIL);
        //SupplierDetailDAOImpl supplierDetailDAO = new SupplierDetailDAOImpl();

        try {
            Material material = new Material(pm.getRawMaterial().getRawMaterialId(), pm.getRawMaterial().getMaterialName(), pm.getRawMaterial().getQty());
            boolean isMaterialSaved = materialDAO.add(material);
            if (isMaterialSaved) {

                List<SupplierDetail> supplierDetailList = new ArrayList<>();
                for (SupplierDetailDTO sd : pm.getSupplierDetails()){
                        supplierDetailList.add(new SupplierDetail(sd.getRawMaterialId(),sd.getSupplierName(),sd.getDate(),sd.getUnitPrice()));
}
                boolean isSupplierDetailSaved = supplierDetailDAO.save(supplierDetailList);
                if (isSupplierDetailSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
