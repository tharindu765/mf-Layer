package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;


import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean isUserExists() throws SQLException {
        try  {
            ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM Users");
            if (resultSet.next()) {
                int userCount = resultSet.getInt(1);
                return userCount > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean registerUser(String username, String password, String ID) throws SQLException {
        if (isUserExists()) {
            return false;
        }
        try {
            return SQLUtil.execute("INSERT INTO Users (username, password, NIC) VALUES (?, ?, ?)",username,password,ID);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean authenticateUser(String username, String password) throws SQLException {
        try {
            ResultSet resultSet = SQLUtil.execute("SELECT * FROM Users WHERE username = ? AND password = ?",username,password);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean truncateUserTable() throws SQLException {
        try {
            return SQLUtil.execute("TRUNCATE TABLE Users");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public boolean checkPassword(Object password) throws SQLException {
        try {
            ResultSet resultSet = SQLUtil.execute("SELECT COUNT(*) FROM Users WHERE Password = ?",password);
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}