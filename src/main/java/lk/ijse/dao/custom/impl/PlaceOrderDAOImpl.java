package lk.ijse.dao.custom.impl;

import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dao.custom.PlaceOrderDAO;
import lk.ijse.dao.custom.SaleDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderDAOImpl implements PlaceOrderDAO {

    public boolean placeOrder(PlaceOrder po) throws SQLException {
        return false;
    }
}
