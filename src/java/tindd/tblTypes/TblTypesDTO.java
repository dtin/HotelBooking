/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblTypes;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblTypesDTO implements Serializable {

    private int typeId;
    private String picture;
    private String typeName;
    private long price;

    public TblTypesDTO() {
    }

    public TblTypesDTO(int typeId, String picture, String typeName, long price) {
        this.typeId = typeId;
        this.picture = picture;
        this.typeName = typeName;
        this.price = price;
    }

    public TblTypesDTO(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public TblTypesDTO(int typeId, String typeName, long price) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.price = price;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

}
