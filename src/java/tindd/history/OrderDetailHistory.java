/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.history;

import java.io.Serializable;
import tindd.tblOrderDetails.TblOrderDetailsDTO;

/**
 *
 * @author Tin
 */
public class OrderDetailHistory implements Serializable {

    private TblOrderDetailsDTO orderDetailsDTO;
    private String roomName;

    public OrderDetailHistory() {
    }

    public OrderDetailHistory(TblOrderDetailsDTO orderDetailsDTO, String roomName) {
        this.orderDetailsDTO = orderDetailsDTO;
        this.roomName = roomName;
    }

    public TblOrderDetailsDTO getOrderDetailsDTO() {
        return orderDetailsDTO;
    }

    public void setOrderDetailsDTO(TblOrderDetailsDTO orderDetailsDTO) {
        this.orderDetailsDTO = orderDetailsDTO;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
