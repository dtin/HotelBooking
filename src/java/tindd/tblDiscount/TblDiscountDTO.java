/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblDiscount;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Tin
 */
public class TblDiscountDTO implements Serializable {

    private String discountCode;
    private float discountPercent;
    private Date availableTo;
    private Date createdAt;

    public TblDiscountDTO() {
    }

    public TblDiscountDTO(String discountCode, float discountPercent, Date availableTo, Date createdAt) {
        this.discountCode = discountCode;
        this.discountPercent = discountPercent;
        this.availableTo = availableTo;
        this.createdAt = createdAt;
    }

    public TblDiscountDTO(String discountCode, float discountPercent, Date availableTo) {
        this.discountCode = discountCode;
        this.discountPercent = discountPercent;
        this.availableTo = availableTo;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Date getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Date availableTo) {
        this.availableTo = availableTo;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
