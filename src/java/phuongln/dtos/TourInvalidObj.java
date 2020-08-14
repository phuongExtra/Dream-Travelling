/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuongln.dtos;

/**
 *
 * @author nhatp
 */
public class TourInvalidObj {

    String nameErr, desErr, priceErr, dateErr, quotaErr;

    public TourInvalidObj() {
    }

    public String getNameErr() {
        return nameErr;
    }

    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public String getDesErr() {
        return desErr;
    }

    public void setDesErr(String desErr) {
        this.desErr = desErr;
    }

    public String getPriceErr() {
        return priceErr;
    }

    public void setPriceErr(String priceErr) {
        this.priceErr = priceErr;
    }

    public String getDateErr() {
        return dateErr;
    }

    public void setDateErr(String dateErr) {
        this.dateErr = dateErr;
    }

    public String getQuotaErr() {
        return quotaErr;
    }

    public void setQuotaErr(String quotaErr) {
        this.quotaErr = quotaErr;
    }

}
