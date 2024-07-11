package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    boolean isUserExists() throws SQLException;

    boolean registerUser(String username, String password, String ID) throws SQLException;

    boolean authenticateUser(String username, String password) throws SQLException;

    boolean truncateUserTable() throws SQLException;

    boolean checkPassword(Object password) throws SQLException;
}
