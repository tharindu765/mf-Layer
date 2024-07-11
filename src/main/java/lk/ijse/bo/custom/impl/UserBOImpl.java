package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {
   UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USERS);
    @Override
    public boolean isUserExists() throws SQLException {
      return userDAO.isUserExists();
       }
    @Override
    public boolean registerUser(String username, String password, String ID) throws SQLException {
    return userDAO.registerUser(username,password,ID);
    }
    @Override
    public boolean authenticateUser(String username, String password) throws SQLException {
    return userDAO.authenticateUser(username,password);
    }

    @Override
    public boolean truncateUserTable() throws SQLException {
        return userDAO.truncateUserTable();
    }
    @Override
    public boolean checkPassword(Object password) throws SQLException {
    return userDAO.checkPassword(password);
    }
}