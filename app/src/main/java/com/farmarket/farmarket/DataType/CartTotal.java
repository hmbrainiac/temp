package com.farmarket.farmarket.DataType;

/**
 * Created by isaac on 2/22/18.
 */

public class CartTotal {
    double total, subTotal;

    public CartTotal() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public CartTotal(double total, double subTotal) {
        this.total = total;
        this.subTotal = subTotal;
    }
}
