/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblOrderDetails;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblOrderDetailsDTO implements Serializable {

    private int detailId;
    private String orderId;
    private int roomId;
    private long price;

    public TblOrderDetailsDTO() {
    }

    public TblOrderDetailsDTO(int detailId, String orderId, int roomId, long price) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.roomId = roomId;
        this.price = price;
    }

    public TblOrderDetailsDTO(String orderId, int roomId, long price) {
        this.orderId = orderId;
        this.roomId = roomId;
        this.price = price;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
