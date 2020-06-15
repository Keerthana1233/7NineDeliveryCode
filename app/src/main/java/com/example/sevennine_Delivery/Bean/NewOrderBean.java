package com.example.sevennine_Delivery.Bean;
public class NewOrderBean {

    String prod_price,prod_name,cod,addr,username;
    String image,Latitude,Longitude,createddate,custlat,custlong;

    public String getCustlat() {
        return custlat;
    }

    public void setCustlat(String custlat) {
        this.custlat = custlat;
    }

    public String getCustlong() {
        return custlong;
    }

    public void setCustlong(String custlong) {
        this.custlong = custlong;
    }

    public NewOrderBean(String prod_name, String username, String addr, String prod_price, String cod, String image, String Latitude, String Longitude, String createddate, String custlat, String custlong) {

        this.prod_price = prod_price;
        this.prod_name = prod_name;
        this.cod = cod;
        this.addr = addr;
        this.username = username;
        this.image = image;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.createddate = createddate;
        this.custlat = custlat;
        this.custlong = custlong;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getImage() {
        return image;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
