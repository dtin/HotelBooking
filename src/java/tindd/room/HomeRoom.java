/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.room;

import java.io.Serializable;
import tindd.tblTypes.TblTypesDTO;

/**
 *
 * @author Tin
 */
public class HomeRoom implements Serializable {

    private TblTypesDTO typesDTO;
    private int quantity;

    public HomeRoom() {
    }

    public HomeRoom(TblTypesDTO typesDTO, int quantity) {
        this.typesDTO = typesDTO;
        this.quantity = quantity;
    }

    public TblTypesDTO getTypesDTO() {
        return typesDTO;
    }

    public void setTypesDTO(TblTypesDTO typesDTO) {
        this.typesDTO = typesDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
