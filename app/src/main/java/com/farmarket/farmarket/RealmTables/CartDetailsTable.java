package com.farmarket.farmarket.RealmTables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 20/02/2018.
 */

public class CartDetailsTable extends RealmObject{
    @PrimaryKey
    int id;

    int cart_id,produce_id;
    double weight,price_per_kg,cost_per_kg;
    String remarks,unique_code,created_at,updated_at,file_name,product_name,produce_type;

    public CartDetailsTable(int id, int cart_id, int produce_id, double weight, double price_per_kg, double cost_per_kg, String remarks, String unique_code, String created_at, String updated_at, String file_name, String product_name, String produce_type) {
        this.id = id;
        this.cart_id = cart_id;
        this.produce_id = produce_id;
        this.weight = weight;
        this.price_per_kg = price_per_kg;
        this.cost_per_kg = cost_per_kg;
        this.remarks = remarks;
        this.unique_code = unique_code;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.file_name = file_name;
        this.product_name = product_name;
        this.produce_type = produce_type;
    }

    public String getProduce_type() {
        return produce_type;
    }

    public void setProduce_type(String produce_type) {
        this.produce_type = produce_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public CartDetailsTable() {
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getProduce_id() {
        return produce_id;
    }

    public void setProduce_id(int produce_id) {
        this.produce_id = produce_id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice_per_kg() {
        return price_per_kg;
    }

    public void setPrice_per_kg(double price_per_kg) {
        this.price_per_kg = price_per_kg;
    }

    public double getCost_per_kg() {
        return cost_per_kg;
    }

    public void setCost_per_kg(double cost_per_kg) {
        this.cost_per_kg = cost_per_kg;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUnique_code() {
        return unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
