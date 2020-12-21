/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblTypes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblTypesDAO implements Serializable {

    public TblTypesDTO getTypeById(int typeId)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        TblTypesDTO typesDTO = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT typeName, picture, price "
                        + "FROM tblTypes "
                        + "WHERE typeId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, typeId);

                rs = stm.executeQuery();

                if (rs.next()) {
                    String typeName = rs.getString("typeName");
                    String picture = rs.getString("picture");
                    long price = rs.getLong("price");

                    typesDTO = new TblTypesDTO(typeId, picture, typeName, price);
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

        return typesDTO;
    }

    public List<TblTypesDTO> getAllTypes()
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        List<TblTypesDTO> typesList = null;
        TblTypesDTO typesDTO;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT typeId, typeName "
                        + "FROM tblTypes";

                stm = conn.prepareStatement(sql);

                rs = stm.executeQuery();

                if (rs.next()) {
                    typesList = new ArrayList<>();

                    do {
                        int typeId = rs.getInt("typeId");
                        String typeName = rs.getString("typeName");
                        typesDTO = new TblTypesDTO(typeId, typeName);

                        typesList.add(typesDTO);
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

        return typesList;
    }

    public TblTypesDTO getCartType(Integer roomType)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        TblTypesDTO typesDTO = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT typeName, price "
                        + "FROM tblTypes "
                        + "WHERE typeId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, roomType);

                rs = stm.executeQuery();
                if (rs.next()) {
                    String typeName = rs.getString("typeName");
                    long price = rs.getLong("price");
                    typesDTO = new TblTypesDTO(roomType, typeName, price);
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

        return typesDTO;
    }

    public long getPrice(Integer typeId)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        long price = 0;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT price "
                        + "FROM tblTypes "
                        + "WHERE typeId=?";

                stm = conn.prepareStatement(sql);
                stm.setInt(1, typeId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    price = rs.getLong("price");
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

        return price;
    }
}
