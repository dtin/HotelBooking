/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblOrders;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblOrdersDAO implements Serializable {

    public List<TblOrdersDTO> getOrderHistory(String userId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblOrdersDTO> orderList = null;
        TblOrdersDTO ordersDTO;

        try {
            conn = DBHelper.makeConnection();

            if (conn != null) {
                String sql = "SELECT orderId, totalPrice, discountCode, checkIn, checkOut, createdAt, feedBack, activationCode "
                        + "FROM tblOrders "
                        + "WHERE userId=? AND status=? "
                        + "ORDER BY createdAt DESC";

                stm = conn.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setBoolean(2, true);

                rs = stm.executeQuery();

                if (rs.next()) {
                    orderList = new ArrayList<>();

                    do {
                        String orderId = rs.getString("orderId");
                        long totalPrice = rs.getLong("totalPrice");
                        String discountCode = rs.getString("discountCode");
                        Date checkIn = rs.getDate("checkIn");
                        Date checkOut = rs.getDate("checkOut");
                        Date createdAt = rs.getDate("createdAt");
                        int feedBack = rs.getInt("feedBack");
                        String activationCode = rs.getString("activationCode");

                        ordersDTO = new TblOrdersDTO(orderId, userId, totalPrice, discountCode, checkIn, checkOut, createdAt, feedBack, activationCode);
                        orderList.add(ordersDTO);
                    } while (rs.next());
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

        return orderList;
    }

    public List<TblOrdersDTO> getOrderHistory(String userId, String orderIdPar, String createdAtPar)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblOrdersDTO> orderList = null;
        TblOrdersDTO ordersDTO;

        try {
            conn = DBHelper.makeConnection();

            if (conn != null) {
                String sql = "SELECT orderId, totalPrice, discountCode, checkIn, checkOut, createdAt, feedBack, activationCode "
                        + "FROM tblOrders "
                        + "WHERE userId=? AND status=? AND orderId LIKE ? AND createdAt=? "
                        + "ORDER BY createdAt DESC";

                stm = conn.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setBoolean(2, true);
                stm.setString(3, "%" + orderIdPar + "%");
                stm.setDate(4, Date.valueOf(createdAtPar));

                rs = stm.executeQuery();

                if (rs.next()) {
                    orderList = new ArrayList<>();

                    do {
                        String orderId = rs.getString("orderId");
                        long totalPrice = rs.getLong("totalPrice");
                        String discountCode = rs.getString("discountCode");
                        Date checkIn = rs.getDate("checkIn");
                        Date checkOut = rs.getDate("checkOut");
                        Date createdAt = rs.getDate("createdAt");
                        int feedBack = rs.getInt("feedBack");
                        String activationCode = rs.getString("activationCode");

                        ordersDTO = new TblOrdersDTO(orderId, userId, totalPrice, discountCode, checkIn, checkOut, createdAt, feedBack, activationCode);
                        orderList.add(ordersDTO);
                    } while (rs.next());
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

        return orderList;
    }

    public boolean isDiscountBefore(String userId, String discountCode)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        boolean isUsedBefore = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT discountCode "
                        + "FROM tblOrders "
                        + "WHERE userId=? AND discountCode=?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userId);
                stm.setString(2, discountCode);

                rs = stm.executeQuery();
                if (rs.next()) {
                    isUsedBefore = true;
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

        return isUsedBefore;
    }

    public String createOrder(String userId, String checkIn, String checkOut, long totalPrice, String discountCode, String activationCode)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String result = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String getOrderIdSql = "SELECT newId() AS orderId";

                stm = conn.prepareStatement(getOrderIdSql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String orderId = rs.getString("orderId");

                    String sql = "INSERT INTO tblOrders(orderId, userId, totalPrice, discountCode, checkIn, checkOut, createdAt, status, activationCode) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, orderId);
                    stm.setString(2, userId);
                    stm.setLong(3, totalPrice);
                    stm.setString(4, discountCode);
                    stm.setDate(5, Date.valueOf(checkIn));
                    stm.setDate(6, Date.valueOf(checkOut));
                    stm.setDate(7, Date.valueOf(LocalDate.now()));
                    stm.setBoolean(8, true);
                    stm.setString(9, activationCode);

                    int rowCount = stm.executeUpdate();
                    if (rowCount > 0) {
                        result = orderId;
                    }
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

        return result;
    }

    public boolean deleteOrder(String orderId, String userId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblOrders "
                        + "SET status=? "
                        + "WHERE orderId=? AND userId=?";
                stm = conn.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setString(2, orderId);
                stm.setString(3, userId);

                int rowCount = stm.executeUpdate();
                if (rowCount > 0) {
                    result = true;
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

    public String getActivationCode(String userId, String orderId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String activationCode = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT activationCode "
                        + "FROM tblOrders "
                        + "WHERE orderId=? AND userId=?";

                stm = conn.prepareStatement(sql);
                stm.setString(1, orderId);
                stm.setString(2, userId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    activationCode = rs.getString("activationCode");
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

        return activationCode;
    }

    public boolean setActiveOrder(String orderId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblOrders "
                        + "SET activationCode=? "
                        + "WHERE orderId=?";

                stm = conn.prepareStatement(sql);
                stm.setString(1, "");
                stm.setString(2, orderId);

                int rowCount = stm.executeUpdate();
                if (rowCount > 0) {
                    result = true;
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

    public boolean validFeedback(String orderId, String userId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT status "
                        + "FROM tblOrders "
                        + "WHERE orderId=? AND userId=? AND checkOut < ?";

                stm = conn.prepareStatement(sql);
                stm.setString(1, orderId);
                stm.setString(2, userId);
                stm.setDate(3, Date.valueOf(LocalDate.now()));

                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
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

        return result;
    }

    public boolean setFeedback(String orderId, int star)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "UPDATE tblOrders "
                        + "SET feedBack=? "
                        + "WHERE orderId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, star);
                stm.setString(2, orderId);

                int rowCount = stm.executeUpdate();
                if (rowCount > 0) {
                    result = true;
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

    public Integer getFeedbackBefore(String orderId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        Integer star = 0;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT feedBack "
                        + "FROM tblOrders "
                        + "WHERE orderId=?";

                stm = conn.prepareStatement(sql);
                stm.setString(1, orderId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    star = rs.getInt("feedBack");
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

        return star;
    }

    public boolean isAllowHistory(String orderId, String userId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        boolean result = false;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT status "
                        + "FROM tblOrders "
                        + "WHERE orderId=? AND userId=?";

                stm = conn.prepareStatement(sql);
                stm.setString(1, orderId);
                stm.setString(2, userId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
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

        return result;
    }
}
