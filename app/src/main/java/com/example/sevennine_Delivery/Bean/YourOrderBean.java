package com.example.sevennine_Delivery.Bean;

public class YourOrderBean {

    String date,price,orderid;

    public YourOrderBean(String date,String price,String orderid) {

        this.price = price;
        this.orderid = orderid;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

}
