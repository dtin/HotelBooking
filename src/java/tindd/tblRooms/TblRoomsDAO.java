/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblRooms;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblRoomsDAO implements Serializable {

    public Map<Integer, Integer> getAllRoom()
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        Map<Integer, Integer> roomsList = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT typeId, COUNT(roomId) AS totalRoom "
                        + "FROM tblRooms "
                        + "GROUP BY typeId";

                stm = conn.prepareStatement(sql);

                rs = stm.executeQuery();

                if (rs.next()) {
                    roomsList = new HashMap<>();

                    do {
                        int typeId = rs.getInt("typeId");
                        int totalRoom = rs.getInt("totalRoom");

                        roomsList.put(typeId, totalRoom);
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

        return roomsList;
    }

    public Map<Integer, Integer> searchRoom(String checkIn, String checkOut, Integer roomType, Integer amountRoom)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        Map<Integer, Integer> listRooms = null;

        int index = 0;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT typeId, COUNT(roomId) AS availableRoom "
                        + "FROM tblRooms "
                        + "WHERE roomId NOT IN (SELECT roomId "
                        + "FROM tblOrderDetails "
                        + "WHERE orderId IN (SELECT orderId "
                        + "FROM tblOrders "
                        + "WHERE checkOut >= ? AND checkIn <= ? AND status=?))";

                if (roomType != null) {
                    sql += " AND typeId=?";
                }

                sql += " GROUP BY typeId";

                if (amountRoom != null) {
                    sql += " HAVING COUNT(roomId) >= ?";
                }

                stm = conn.prepareStatement(sql);

                stm.setDate(++index, Date.valueOf(checkIn));
                stm.setDate(++index, Date.valueOf(checkOut));
                stm.setBoolean(++index, true);

                if (roomType != null) {
                    stm.setInt(++index, roomType);
                }

                if (amountRoom != null) {
                    stm.setInt(++index, amountRoom);
                }

                rs = stm.executeQuery();

                if (rs.next()) {
                    listRooms = new HashMap<>();
                    do {
                        int typeId = rs.getInt("typeId");
                        int availableRoom = rs.getInt("availableRoom");

                        listRooms.put(typeId, availableRoom);
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

        return listRooms;
    }

    public int getQuantityAvailable(Integer typeId, String checkIn, String checkOut)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        int availableRoom = 0;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT COUNT(roomId) AS availableRoom "
                        + "FROM tblRooms "
                        + "WHERE roomId NOT IN (SELECT roomId "
                        + "FROM tblOrderDetails "
                        + "WHERE orderId IN (SELECT orderId "
                        + "FROM tblOrders "
                        + "WHERE checkOut >= ? AND checkIn <= ? AND status=?)) "
                        + "AND typeId=?";

                stm = conn.prepareStatement(sql);

                stm.setDate(1, Date.valueOf(checkIn));
                stm.setDate(2, Date.valueOf(checkOut));
                stm.setBoolean(3, true);
                stm.setInt(4, typeId);

                rs = stm.executeQuery();

                if (rs.next()) {
                    availableRoom = rs.getInt("availableRoom");
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

        return availableRoom;
    }

    public List<Integer> getListRoomAvailable(Integer typeId, int buyQuantity, String checkIn, String checkOut)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<Integer> roomsId = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT TOP " + buyQuantity + " roomId "
                        + "FROM tblRooms "
                        + "WHERE roomId NOT IN (SELECT roomId "
                        + "FROM tblOrderDetails "
                        + "WHERE orderId IN (SELECT orderId "
                        + "FROM tblOrders "
                        + "WHERE checkOut >= ? AND checkIn <= ? AND status=?)) "
                        + "AND typeId=?";

                stm = conn.prepareStatement(sql);

                stm.setDate(1, Date.valueOf(checkIn));
                stm.setDate(2, Date.valueOf(checkOut));
                stm.setBoolean(3, true);
                stm.setInt(4, typeId);

                rs = stm.executeQuery();

                if (rs.next()) {
                    roomsId = new ArrayList<>();

                    do {
                        int roomId = rs.getInt("roomId");
                        roomsId.add(roomId);
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

        return roomsId;
    }

    public String getRoomNumber(int roomId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        String roomNumber = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT roomNumber FROM tblRooms WHERE roomId=?";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, roomId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    roomNumber = rs.getString("roomNumber");
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

        return roomNumber;
    }
}
