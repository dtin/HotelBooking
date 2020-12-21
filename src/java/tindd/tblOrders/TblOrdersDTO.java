/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblOrders;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Tin
 */
public class TblOrdersDTO implements Serializable {

    private String orderId;
    private String userId;
    private long totalPrice;
    private String discountCode;
    private Date checkIn;
    private Date checkOut;
    private Date createdAt;
    private int feedBack;
    private boolean status;
    private String activationCode;

    public TblOrdersDTO() {
    }

    public TblOrdersDTO(String orderId, String userId, long totalPrice, String discountCode, Date checkIn, Date checkOut, Date createdAt, int feedBack, boolean status, String activationCode) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.discountCode = discountCode;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdAt = createdAt;
        this.feedBack = feedBack;
        this.status = status;
        this.activationCode = activationCode;
    }

    public TblOrdersDTO(String orderId, String userId, long totalPrice, String discountCode, Date checkIn, Date checkOut, Date createdAt, int feedBack, String activationCode) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.discountCode = discountCode;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdAt = createdAt;
        this.feedBack = feedBack;
        this.activationCode = activationCode;
    }
    
    

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(int feedBack) {
        this.feedBack = feedBack;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

}
