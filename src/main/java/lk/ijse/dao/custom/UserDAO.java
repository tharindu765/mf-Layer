package lk.ijse.dao.custom;

import lk.ijse.dao.SuperDAO;

import java.sql.SQLException;

public interface UserDAO extends SuperDAO {
    boolean isUserExists() throws SQLException;

    boolean registerUser(String username, String password, String ID) throws SQLException;

    boolean authenticateUser(String username, String password) throws SQLException;

    boolean truncateUserTable() throws SQLException;

    boolean checkPassword(Object password) throws SQLException;
}
