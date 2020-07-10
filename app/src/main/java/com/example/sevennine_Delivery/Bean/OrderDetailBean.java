package com.example.sevennine_Delivery.Bean;

public class OrderDetailBean {

    String prod_name,quantity,amount,shipping_fee,shippng_discount,prod_img;

    public OrderDetailBean(String prod_name, String quantity,String amount,String shipping_fee,String shippng_discount, String prod_img) {

        this.prod_name = prod_name;
        this.quantity = quantity;
        this.prod_img = prod_img;
        this.shipping_fee = shipping_fee;
        this.shippng_discount = shippng_discount;
        this.amount = amount;
        this.prod_img = prod_img;
    }

    public String getProd_name() {
        return prod_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
    }

    public String getShipping_fee() {
        return shipping_fee;
    }

    public String getShippng_discount() {
        return shippng_discount;
    }

    public String getProd_img() {
        return prod_img;
    }
}
