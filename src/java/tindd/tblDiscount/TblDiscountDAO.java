/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblDiscount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tindd.utils.DBHelper;

/**
 *
 * @author Tin
 */
public class TblDiscountDAO implements Serializable {

    //Check and get discount percent
    public TblDiscountDTO getDiscount(String discountCode)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblDiscountDTO discountDTO = null;

        try {
            conn = DBHelper.makeConnection();
            if (conn != null) {
                String sql = "SELECT discountPercent, availableTo "
                        + "FROM tblDiscounts "
                        + "WHERE discountCode=?";

                stm = conn.prepareStatement(sql);
                stm.setString(1, discountCode);

                rs = stm.executeQuery();
                if (rs.next()) {
                    float discountPercent = rs.getFloat("discountPercent");
                    Date availableTo = rs.getDate("availableTo");
                    discountDTO = new TblDiscountDTO(discountCode, discountPercent, availableTo);
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

        return discountDTO;
    }
}
