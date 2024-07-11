package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {
    String getCustomerID(String name) throws SQLException, ClassNotFoundException;

    List<String> getCustomerID() throws SQLException, ClassNotFoundException;

    String setCustomerName(String id) throws SQLException, ClassNotFoundException;

    String getCustomerID(int orderID) throws SQLException, ClassNotFoundException;

    int getCustomerCount() throws SQLException, ClassNotFoundException;
}
