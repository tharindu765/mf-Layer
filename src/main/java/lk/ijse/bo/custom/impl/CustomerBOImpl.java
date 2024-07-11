package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.db.DBConnection;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public List<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> allCUS = new ArrayList<>();
        List<Customer>cus = customerDAO.getAll();

        for (Customer c : cus){
            allCUS.add(new CustomerDTO(c.getCustomerID(),c.getName(),c.getAddress(),c.getTelNumber()));
        }
        return allCUS;
    }

    @Override
    public boolean add(CustomerDTO customer) throws SQLException, ClassNotFoundException {

        return customerDAO.add(new Customer(customer.getCustomerID(), customer.getName(), customer.getAddress(), customer.getTelNumber()));
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean update(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customer.getCustomerID(), customer.getName(), customer.getAddress(), customer.getTelNumber()));
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.search(id);
    }

    @Override
    public boolean exist(String name) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(name);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getCustomerID(String name) throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerID(name);
    }

    @Override
    public List<String> getCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerID();
    }

    @Override
    public String setCustomerName(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.setCustomerName(id);
    }

    @Override
    public String getCustomerID(int orderID) throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerID(orderID);
    }

    @Override
    public int getCustomerCount() throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerCount();
    }
}