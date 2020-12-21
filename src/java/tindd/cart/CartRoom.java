/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.cart;

import java.io.Serializable;
import tindd.tblTypes.TblTypesDTO;

/**
 *
 * @author Tin
 */
public class CartRoom implements Serializable {

    private TblTypesDTO typesDTO;
    private int roomAmount;

    public CartRoom() {
    }

    public CartRoom(TblTypesDTO typesDTO, int roomAmount) {
        this.typesDTO = typesDTO;
        this.roomAmount = roomAmount;
    }

    public TblTypesDTO getTypesDTO() {
        return typesDTO;
    }

    public void setTypesDTO(TblTypesDTO typesDTO) {
        this.typesDTO = typesDTO;
    }

    public int getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(int roomAmount) {
        this.roomAmount = roomAmount;
    }

}
