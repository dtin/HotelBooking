/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblUsers;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.naming.NamingException;
import tindd.tblRoles.TblRolesDAO;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblUsersDAO implements Serializable {

    public TblUsersDTO checkLogin(String userId, String password)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblUsersDTO dto = null;

        try {
            conn = DBHelper.makeConnection();

            if (conn != null) {
                String sql = "SELECT name, address, phone, status, roleId "
                        + "FROM tblUsers "
                        + "WHERE userId=? AND password=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, password);

                rs = stm.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    boolean status = rs.getBoolean("status");
                    int roleId = rs.getInt("roleId");
                    dto = new TblUsersDTO(userId, password, name, address, name, status, roleId);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

        return dto;
    }

    public boolean createAccount(String userId, String password, String name, String address, String phone)
            throws SQLException, NamingException {
        boolean result = false;

        Connection conn = null;
        PreparedStatement stm = null;

        TblRolesDAO rolesDAO;
        Integer roleId;

        try {
            rolesDAO = new TblRolesDAO();
            roleId = rolesDAO.getRoleId("Customer");

            if (roleId != null) {
                conn = DBHelper.makeConnection();

                if (conn != null) {
                    String sql = "INSERT INTO tblUsers(userId, password, name, address, phone, status, roleId, createdAt) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                    stm = conn.prepareStatement(sql);

                    stm.setString(1, userId);
                    stm.setString(2, password);
                    stm.setString(3, name);
                    stm.setString(4, address);
                    stm.setString(5, phone);
                    stm.setBoolean(6, true);
                    stm.setInt(7, roleId);
                    stm.setDate(8, Date.valueOf(LocalDate.now()));

                    int rowCount = stm.executeUpdate();
                    if (rowCount > 0) {
                        result = true;
                    }
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;
    }

    public boolean createAccount(String userId, String name)
            throws SQLException, NamingException {
        boolean result = false;

        Connection conn = null;
        PreparedStatement stm = null;

        TblRolesDAO rolesDAO;
        Integer roleId;

        try {
            rolesDAO = new TblRolesDAO();
            roleId = rolesDAO.getRoleId("Customer");

            if (roleId != null) {
                conn = DBHelper.makeConnection();

                if (conn != null) {
                    String sql = "INSERT INTO tblUsers(userId, password, name, status, roleId, createdAt) "
                            + "VALUES(?, ?, ?, ?, ?, ?)";
                    stm = conn.prepareStatement(sql);

                    stm.setString(1, userId);
                    stm.setString(2, "");
                    stm.setString(3, name);
                    stm.setBoolean(4, true);
                    stm.setInt(5, roleId);
                    stm.setDate(6, Date.valueOf(LocalDate.now()));

                    int rowCount = stm.executeUpdate();
                    if (rowCount > 0) {
                        result = true;
                    }
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return result;

    }
}
