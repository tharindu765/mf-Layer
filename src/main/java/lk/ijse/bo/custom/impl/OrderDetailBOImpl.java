package lk.ijse.bo.custom.impl;


import lk.ijse.bo.custom.OrderDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.tdm.OrderTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBOImpl implements OrderDetailBO {
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    @Override
    public boolean save(List<OrderDetailDTO> odList) throws SQLException, ClassNotFoundException {
        List<OrderDetail> allCUS = new ArrayList<>();
        for (OrderDetailDTO c : odList){
            allCUS.add(new OrderDetail(c.getPackageID(),c.getOrderID(),c.getTotalPrice(),c.getQty()));
        }
        return orderDetailDAO.save(allCUS);
    }
    @Override
    public boolean Save(int packageID, int orderID, double totalPrice, int qty) throws SQLException, ClassNotFoundException {
    return orderDetailDAO.Save(packageID,orderID,totalPrice,qty);
    }


    @Override
    public boolean updateOrderDetail(OrderTm selectedOrder, int packageId) throws SQLException, ClassNotFoundException {
    return orderDetailDAO.updateOrderDetail(selectedOrder,packageId);
    }

    @Override
    public double calculateTotalPrice(int orderID) throws SQLException, ClassNotFoundException {
    return orderDetailDAO.calculateTotalPrice(orderID);
    }

    @Override
    public List<OrderDetailDTO> getOrderDetailsByOrderID(int orderID) throws SQLException, ClassNotFoundException {
       List<OrderDetailDTO> orderDetails = new ArrayList<>();
        List<OrderDetail> orderDetailsByOrderID = orderDetailDAO.getOrderDetailsByOrderID(orderID);
        for (OrderDetail o:orderDetailsByOrderID){
             orderDetails.add(new OrderDetailDTO(o.getPackageID(),o.getOrderID(),o.getTotalPrice(),o.getQty()));
        }
       return orderDetails;
    }

    @Override
    public List<OrderDetailDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(OrderDetailDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetailDTO entity) throws SQLException, ClassNotFoundException {
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
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
