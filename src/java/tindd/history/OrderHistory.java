/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.history;

import java.io.Serializable;
import tindd.tblOrders.TblOrdersDTO;

/**
 *
 * @author Tin
 */
public class OrderHistory implements Serializable {

    private TblOrdersDTO ordersDTO;
    private long afterDiscountPrice;
    private String action;

    public OrderHistory() {
    }

    public OrderHistory(TblOrdersDTO ordersDTO, long afterDiscountPrice, String action) {
        this.ordersDTO = ordersDTO;
        this.afterDiscountPrice = afterDiscountPrice;
        this.action = action;
    }

    public TblOrdersDTO getOrdersDTO() {
        return ordersDTO;
    }

    public void setOrdersDTO(TblOrdersDTO ordersDTO) {
        this.ordersDTO = ordersDTO;
    }

    public long getAfterDiscountPrice() {
        return afterDiscountPrice;
    }

    public void setAfterDiscountPrice(long afterDiscountPrice) {
        this.afterDiscountPrice = afterDiscountPrice;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
