/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.utils;

import java.io.Serializable;
import java.util.HashMap;
import phuongln.dtos.BookingDTO;

/**
 *
 * @author nhatp
 */
public class CartObject implements Serializable {

    private String customerName;
    private HashMap<String, BookingDTO> cart;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public HashMap<String, BookingDTO> getCart() {
        return cart;
    }

    public CartObject() {
        this.cart = new HashMap<>();
    }

    public CartObject(String customerName) {
        this.customerName = customerName;
        this.cart = new HashMap<>();
    }

    public int size() {
        return cart.size();
    }

    public void addToCart(BookingDTO dto) throws Exception {
        if (this.cart.containsKey(dto.getTourID())) {
            int quantity = this.cart.get(dto.getTourID()).getAmount() + dto.getAmount();
            dto.setAmount(quantity);
        }
        this.cart.put(dto.getTourID(), dto);
    }

    public boolean delete(String id) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
            return true;
        } else {
            return false;
        }
    }

    public int getTotal() {
        int result = 0;
        for (BookingDTO dto : this.cart.values()) {
            result += dto.getTotal();
        }
        return result;
    }

    public boolean update(String id, int quantity) throws Exception {
        if (this.cart.containsKey(id)) {
            this.cart.get(id).setAmount(quantity);
            this.cart.get(id).setTotal(quantity * this.cart.get(id).getPrice());
            return true;
        }
        return false;
    }

}
