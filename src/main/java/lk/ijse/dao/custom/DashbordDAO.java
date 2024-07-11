package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.sql.SQLException;

public interface DashbordDAO extends SuperDAO {
    double getCurrentMonthProfit() throws SQLException;
}
