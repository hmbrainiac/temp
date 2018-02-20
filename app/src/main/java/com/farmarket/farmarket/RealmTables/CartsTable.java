package com.farmarket.farmarket.RealmTables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 20/02/2018.
 */

public class CartsTable extends RealmObject{

    @PrimaryKey
    int id;
    int user_id,delivery_id;
    String expected_delivery,status,delivery_lon,delivery_lat,delivery_gh_post_code,phone,email,name,cart_status;

    public CartsTable(int id, int user_id, int delivery_id, String expected_delivery, String status, String delivery_lon, String delivery_lat, String delivery_gh_post_code, String phone, String email, String name, String cart_status) {
        this.id = id;
        this.user_id = user_id;
        this.delivery_id = delivery_id;
        this.expected_delivery = expected_delivery;
        this.status = status;
        this.delivery_lon = delivery_lon;
        this.delivery_lat = delivery_lat;
        this.delivery_gh_post_code = delivery_gh_post_code;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.cart_status = cart_status;
    }

    public CartsTable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getExpected_delivery() {
        return expected_delivery;
    }

    public void setExpected_delivery(String expected_delivery) {
        this.expected_delivery = expected_delivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery_lon() {
        return delivery_lon;
    }

    public void setDelivery_lon(String delivery_lon) {
        this.delivery_lon = delivery_lon;
    }

    public String getDelivery_lat() {
        return delivery_lat;
    }

    public void setDelivery_lat(String delivery_lat) {
        this.delivery_lat = delivery_lat;
    }

    public String getDelivery_gh_post_code() {
        return delivery_gh_post_code;
    }

    public void setDelivery_gh_post_code(String delivery_gh_post_code) {
        this.delivery_gh_post_code = delivery_gh_post_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCart_status() {
        return cart_status;
    }

    public void setCart_status(String cart_status) {
        this.cart_status = cart_status;
    }
}
