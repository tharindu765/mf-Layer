package lk.ijse.bo.custom.impl;



import lk.ijse.bo.custom.DashbordBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.DashbordDAO;

import java.sql.SQLException;

public class DashbordBOImpl implements DashbordBO {
DashbordDAO dashbordDAO = (DashbordDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Dashbord);
    @Override
    public double getCurrentMonthProfit() throws SQLException {
    return dashbordDAO.getCurrentMonthProfit();
        }
}
