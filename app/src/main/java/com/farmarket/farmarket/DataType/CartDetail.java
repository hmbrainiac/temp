package com.farmarket.farmarket.DataType;

import java.io.Serializable;

/**
 * Created by isaac on 2/22/18.
 */

public class CartDetail implements Serializable{
    int id;

    int detail_id,order_id,produce_id;
    double weight,price_per_kg,cost_per_kg;
    String remarks,unique_code,created_at,updated_at,file_name,product_type,product_name;

    public CartDetail(int id, int detail_id, int order_id, int produce_id, double weight, double price_per_kg, double cost_per_kg, String remarks, String unique_code, String created_at, String updated_at, String file_name, String product_type) {
        this.id = id;
        this.detail_id = detail_id;
        this.order_id = order_id;
        this.produce_id = produce_id;
        this.weight = weight;
        this.price_per_kg = price_per_kg;
        this.cost_per_kg = cost_per_kg;
        this.remarks = remarks;
        this.unique_code = unique_code;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.file_name = file_name;
        this.product_type = product_type;
    }

    public CartDetail(int id, int detail_id, int order_id, int produce_id, double weight, double price_per_kg, double cost_per_kg, String remarks, String unique_code, String created_at, String updated_at, String file_name, String product_type, String product_name) {
        this.id = id;
        this.detail_id = detail_id;
        this.order_id = order_id;
        this.produce_id = produce_id;
        this.weight = weight;
        this.price_per_kg = price_per_kg;
        this.cost_per_kg = cost_per_kg;
        this.remarks = remarks;
        this.unique_code = unique_code;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.file_name = file_name;
        this.product_type = product_type;
        this.product_name = product_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public CartDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(int detail_id) {
        this.detail_id = detail_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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
