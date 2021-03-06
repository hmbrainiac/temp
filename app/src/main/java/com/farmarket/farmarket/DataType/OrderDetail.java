package com.farmarket.farmarket.DataType;

import java.io.Serializable;

/**
 * Created by admin on 27/02/2018.
 */

public class OrderDetail  implements Serializable {
    int detail_id,order_id,produce_id;
    double weight,price_per_kg,cost_per_kg,incremental;
    String remarks,unique_code,created_at,updated_at,measureemt;
    Product produce;

    public OrderDetail(int detail_id, int order_id, int produce_id, double weight, double price_per_kg, double cost_per_kg, double incremental, String remarks, String unique_code, String created_at, String updated_at, String measureemt, Product produce) {
        this.detail_id = detail_id;
        this.order_id = order_id;
        this.produce_id = produce_id;
        this.weight = weight;
        this.price_per_kg = price_per_kg;
        this.cost_per_kg = cost_per_kg;
        this.incremental = incremental;
        this.remarks = remarks;
        this.unique_code = unique_code;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.measureemt = measureemt;
        this.produce = produce;
    }

    public double getIncremental() {
        return incremental;
    }

    public void setIncremental(double incremental) {
        this.incremental = incremental;
    }

    public String getMeasureemt() {
        return measureemt;
    }

    public void setMeasureemt(String measureemt) {
        this.measureemt = measureemt;
    }

    public OrderDetail(int detail_id, int order_id, int produce_id, double weight, double price_per_kg, double cost_per_kg, String remarks, String unique_code, String created_at, String updated_at, Product produce) {
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
        this.produce = produce;
    }

    public OrderDetail(int detail_id, int order_id, int produce_id, double weight, double price_per_kg, double cost_per_kg, String remarks, String unique_code, String created_at, String updated_at) {
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
    }

    public Product getProduce() {
        return produce;
    }

    public void setProduce(Product produce) {
        this.produce = produce;
    }

    public OrderDetail() {
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
