package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.OrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.Order;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
   OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    @Override
    public List<OrderDTO> getAll() throws SQLException, ClassNotFoundException {
        List<OrderDTO> allCUS = new ArrayList<>();
        List<Order>cus = orderDAO.getAll();

        for (Order c : cus){
            allCUS.add(new OrderDTO(c.getDate(), c.getIncenseType(), c.getCustomerName(), c.getQty(), c.getTotalPrice(), c.getOrderId(), c.getCustomerId()));
        }
        return allCUS;
    }

    @Override
    public boolean add(OrderDTO entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDTO entity) throws SQLException, ClassNotFoundException {
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
    public OrderDTO search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDTO c) throws SQLException, ClassNotFoundException {
        Order order = new Order(c.getDate(), c.getIncenseType(), c.getCustomerName(), c.getQty(), c.getTotalPrice(), c.getOrderId(), c.getCustomerId());
        return orderDAO.save(order);
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return orderDAO.getCurrentId();
    }
    @Override
    public boolean addOrder(String orderId, Date date, String customerName) throws SQLException, ClassNotFoundException {
        return orderDAO.addOrder(orderId,date,customerName);
    }
    @Override
    public List<OrderDTO> searchById(String cuID) throws SQLException, ClassNotFoundException {
        List<Order> orders = orderDAO.searchById(cuID);
        List<OrderDTO> orderDTOList =new ArrayList<>();
        for (Order c : orders){
            OrderDTO order = new OrderDTO(c.getDate(), c.getIncenseType(), c.getCustomerName(), c.getQty(), c.getTotalPrice(), c.getOrderId(), c.getCustomerId());
            orderDTOList.add(order);
        }
        return orderDTOList;
    }
    @Override
    public List<String> getOrderId() throws SQLException, ClassNotFoundException {
    return orderDAO.getOrderId();
    }
    @Override
    public boolean updateCustomer(int orderID, String newCustomerId) throws SQLException, ClassNotFoundException {
    return orderDAO.updateCustomer(orderID,newCustomerId);
    }
    @Override
    public double calculateNetWorth() throws SQLException, ClassNotFoundException {
        return orderDAO.calculateNetWorth();
    }
    @Override
    public int getOrderCount() throws SQLException, ClassNotFoundException {
        return orderDAO.getOrderCount();
    }
    @Override
    public String getCustomerIDFromOrderID(String orderId) throws SQLException, ClassNotFoundException {
    return orderDAO.getCustomerIDFromOrderID(orderId);
    }
}

