package com.example.sevennine_Delivery.Bean;

public class OrderDetailBean {

    String prod_name,quantity,amount,shipping_fee,shippng_iscount,prod_img;

    public OrderDetailBean(String prod_name, String quantity,String amount,String shipping_fee,String shippng_iscount, String prod_img) {

        this.prod_name = prod_name;
        this.quantity = quantity;
        this.prod_img = prod_img;
        this.shippng_iscount = shippng_iscount;
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

    public String getShippng_iscount() {
        return shippng_iscount;
    }

    public String getProd_img() {
        return prod_img;
    }
}
