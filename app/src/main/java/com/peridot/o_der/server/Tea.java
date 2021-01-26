package com.peridot.o_der.server;

//디저트 리사이클러뷰 이름, 가격

public class Tea {
    String id;
    String name;
    String price;
    String imgPath;

    public Tea(String id, String name, String price, String imgPath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgPath = imgPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}


