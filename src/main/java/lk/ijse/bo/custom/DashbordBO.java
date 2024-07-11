package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.sql.SQLException;

public interface DashbordBO extends SuperBO {
    double getCurrentMonthProfit() throws SQLException;
}
