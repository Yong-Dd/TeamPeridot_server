package com.peridot.o_der.server;

public class OrderList {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String name;          // 고객 이름
    String tel;
    String order_date;    // 주문 날짜
    String product_name;   // 제품 이름
    String price;            //  가격
    String pickUp_time;   // 픽업 시간
    String memo;          // 메모

    public OrderList(int id, String name,String tel ,String order_date, String product_name, String price, String pickUp_time, String memo) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.order_date = order_date;
        this.product_name = product_name;
        this.price = price;
        this.pickUp_time = pickUp_time;
        this.memo = memo;

    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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
