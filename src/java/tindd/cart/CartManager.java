/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.cart;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tin
 */
public class CartManager implements Serializable {

    //Key: room type
    //Value: number of rooms
    Map<Integer, Integer> cartManager;
    Date checkInDate;
    Date checkOutDate;

    public CartManager() {
    }

    public Map<Integer, Integer> getCartManager() {
        return cartManager;
    }

    public boolean addToCart(int productId) {
        Integer quantity = 1;

        if (cartManager == null) {
            cartManager = new HashMap<>();
            cartManager.put(productId, quantity);
        } else {
            if (cartManager.containsKey(productId)) {
                quantity = cartManager.get(productId);
                cartManager.put(productId, quantity + 1);
            } else {
                cartManager.put(productId, quantity);
            }
        }

        return true;
    }

    public boolean deleteFromCart(int productId) {
        boolean result = false;

        if (cartManager != null) {
            if (cartManager.containsKey(productId)) {
                cartManager.remove(productId);

                result = true;
            }
        }

        return result;
    }

    public boolean isEmptyCart() {
        boolean result = false;

        if (cartManager.isEmpty()) {
            cartManager = null;
            result = true;
        }

        return result;
    }

    public boolean updateItemCart(int productId, int quantity) {
        boolean result = false;

        if (cartManager != null) {
            if (cartManager.containsKey(productId)) {
                if (quantity <= 0) {
                    cartManager.put(productId, 1);
                } else {
                    cartManager.put(productId, quantity);
                }

                result = true;
            }
        }

        return result;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
