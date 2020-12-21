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
public class TblRoomsDTO implements Serializable {

    private int roomId;
    private int roomNumber;
    private int typeId;

    public TblRoomsDTO() {
    }

    public TblRoomsDTO(int roomId, int roomNumber, int typeId) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.typeId = typeId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
