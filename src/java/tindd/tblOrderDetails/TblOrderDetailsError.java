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
public class TblOrderDetailsError implements Serializable {

    private String roomOutOfStock;

    public TblOrderDetailsError() {
    }

    public TblOrderDetailsError(String roomOutOfStock) {
        this.roomOutOfStock = roomOutOfStock;
    }

    public String getRoomOutOfStock() {
        return roomOutOfStock;
    }

    public void setRoomOutOfStock(String roomOutOfStock) {
        this.roomOutOfStock = roomOutOfStock;
    }
}
