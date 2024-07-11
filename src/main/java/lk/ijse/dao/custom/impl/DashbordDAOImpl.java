package lk.ijse.dao.custom.impl;



import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.DashbordDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DashbordDAOImpl implements DashbordDAO {
    @Override
    public double getCurrentMonthProfit() throws SQLException {
            try {
                ResultSet resultSet = SQLUtil.execute("SELECT Profit FROM CurrentMonthProfit");
                if (resultSet.next()) {
                    return resultSet.getDouble("Profit");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        return 0.0;
        }
}
