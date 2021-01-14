package com.peridot.o_der.server;

public class OrderList {
    String name;          // 고객 이름
    String mobile;        // 고객 번호
    String product_name;   // 제품 이름
    String product_count;    //  제품 개수
    String price;            //  가격
    String pickUp_time;   // 픽업 시간
    String memo;          // 메모

    public OrderList(String name, String mobile, String product_name, String coffe_count, String price, String pickUp_time, String memo) {
        this.name = name;
        this.mobile = mobile;
        this.product_name = product_name;
        this.product_count = coffe_count;
        this.price = price;
        this.pickUp_time = pickUp_time;
        this.memo = memo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_count() {
        return product_count;
    }

    public void setProduct_count(String product_count) {
        this.product_count = product_count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPickUp_time() {
        return pickUp_time;
    }

    public void setPickUp_time(String pickUp_time) {
        this.pickUp_time = pickUp_time;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
