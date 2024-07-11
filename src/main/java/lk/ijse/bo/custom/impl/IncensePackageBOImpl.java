package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.IncensePackageBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.IncensePackageDAO;
import lk.ijse.dto.IncensePackageDTO;
import lk.ijse.entity.IncensePackage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncensePackageBOImpl implements IncensePackageBO {
    IncensePackageDAO incensePackageDAO = (IncensePackageDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.IncensePackage);
    @Override
    public boolean exitPackage(String incenseType) throws SQLException, ClassNotFoundException {
     return incensePackageDAO.exitPackage(incenseType);
       }

    @Override
    public List<String> getIncenseType() throws SQLException, ClassNotFoundException {
        return incensePackageDAO.getIncenseType();
    }
    @Override
    public int getPackageId(String incenseType) throws SQLException, ClassNotFoundException {
    return incensePackageDAO.getPackageId(incenseType);
    }

    @Override
    public List<IncensePackageDTO> getAll() throws SQLException, ClassNotFoundException {
        List<IncensePackageDTO> allCUS = new ArrayList<>();
        List<IncensePackage>cus = incensePackageDAO.getAll();

        for (IncensePackage c : cus){
            allCUS.add(new IncensePackageDTO(c.getPackageID(),c.getDescription(),c.getQty()));
        }
        return allCUS;
        }

    @Override
    public boolean add(IncensePackage entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(IncensePackage entity) throws SQLException, ClassNotFoundException {
        return false;
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
    public IncensePackage search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

}
