/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.dtos;

import java.io.Serializable;

/**
 *
 * @author nhatp
 */
public class DiscountDTO implements Serializable {

    String id, username, code, expireDate;
    int value;

    public DiscountDTO() {
    }

    public DiscountDTO(String code, String expireDate, int value) {
        this.code = code;
        this.expireDate = expireDate;
        this.value = value;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
