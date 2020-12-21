/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblRooms;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblRoomsError implements Serializable {

    private String roomNumberNumberic;
    private String roomTypeNumberic;
    private String amountPositive;

    public TblRoomsError() {
    }

    public TblRoomsError(String roomNumberNumberic, String roomTypeNumberic, String amountPositive) {
        this.roomNumberNumberic = roomNumberNumberic;
        this.roomTypeNumberic = roomTypeNumberic;
        this.amountPositive = amountPositive;
    }

    public String getRoomNumberNumberic() {
        return roomNumberNumberic;
    }

    public void setRoomNumberNumberic(String roomNumberNumberic) {
        this.roomNumberNumberic = roomNumberNumberic;
    }

    public String getRoomTypeNumberic() {
        return roomTypeNumberic;
    }

    public void setRoomTypeNumberic(String roomTypeNumberic) {
        this.roomTypeNumberic = roomTypeNumberic;
    }

    public String getAmountPositive() {
        return amountPositive;
    }

    public void setAmountPositive(String amountPositive) {
        this.amountPositive = amountPositive;
    }

}
