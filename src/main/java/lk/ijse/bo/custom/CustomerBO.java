package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    List<CustomerDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean add(CustomerDTO customer) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    boolean update(CustomerDTO customer) throws SQLException, ClassNotFoundException;

    Customer search(String id) throws SQLException, ClassNotFoundException;

    boolean exist(String name) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    String getCustomerID(String name) throws SQLException, ClassNotFoundException;

    List<String> getCustomerID() throws SQLException, ClassNotFoundException;

    String setCustomerName(String id) throws SQLException, ClassNotFoundException;

    String getCustomerID(int orderID) throws SQLException, ClassNotFoundException;

    int getCustomerCount() throws SQLException, ClassNotFoundException;
}
