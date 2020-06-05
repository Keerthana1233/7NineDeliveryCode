package com.example.sevennine_Delivery.Bean;

public class ListBeanHome {

    String name,image;

    int id;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public ListBeanHome(String name, int id, String image) {
        this.name=name;
        this.id = id;
        this.image=image;
    }
}


