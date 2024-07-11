package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dao.custom.SaleDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.entity.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    @Override
    public boolean placeOrder(PlaceOrder po) throws SQLException {
        OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
        SaleDAO saleDAO = (SaleDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALE);
        OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isOrderSaved = orderDAO.save(po.getOrder());
            if (isOrderSaved) {
                boolean isSaleSaved = saleDAO.add(po.getSale());
                    if (isSaleSaved) {
                        boolean isOrderDetailSaved = orderDetailDAO.save(po.getOdList());
                        if (isOrderDetailSaved) {
                            connection.commit();
                            return true;
                         }
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
