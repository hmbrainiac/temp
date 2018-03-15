package com.farmarket.farmarket.DataType;

import java.io.Serializable;

/**
 * Created by admin on 27/02/2018.
 */

public class Product  implements Serializable {
    int produce_id;
    String uuid,unique_code,name,produce_type,created_at,updated_at,file_name,description,price_per_kg,file_blob;
    double incremental;
    String measurement,measurement_full;
    int category_id;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getMeasurement_full() {
        return measurement_full;
    }

    public void setMeasurement_full(String measurement_full) {
        this.measurement_full = measurement_full;
    }

    public Product(int produce_id, String uuid, String unique_code, String name, String produce_type, String created_at, String updated_at, String file_name, String description, String price_per_kg, String file_blob, double incremental, String measurement, String measurement_full, int category_id) {
        this.produce_id = produce_id;
        this.uuid = uuid;
        this.unique_code = unique_code;
        this.name = name;
        this.produce_type = produce_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.file_name = file_name;
        this.description = description;
        this.price_per_kg = price_per_kg;
        this.file_blob = file_blob;
        this.incremental = incremental;
        this.measurement = measurement;
        this.measurement_full = measurement_full;
        this.category_id = category_id;
    }

    public Product(int produce_id, String uuid, String unique_code, String name, String produce_type, String created_at, String updated_at, String file_name, String description, String price_per_kg, String file_blob, double incremental, String measurement, int category_id) {
        this.produce_id = produce_id;
        this.uuid = uuid;
        this.unique_code = unique_code;
        this.name = name;
        this.produce_type = produce_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.file_name = file_name;
        this.description = description;
        this.price_per_kg = price_per_kg;
        this.file_blob = file_blob;
        this.incremental = incremental;
        this.measurement = measurement;
        this.category_id = category_id;
    }

    public double getIncremental() {
        return incremental;
    }

    public void setIncremental(double incremental) {
        this.incremental = incremental;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Product(int produce_id, double incremental, String uuid, String unique_code, String name, String produce_type, String created_at, String updated_at, String file_name, String description, String price_per_kg, String file_blob, String measurement) {
        this.produce_id = produce_id;
        this.incremental = incremental;
        this.uuid = uuid;
        this.unique_code = unique_code;
        this.name = name;
        this.produce_type = produce_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.file_name = file_name;
        this.description = description;
        this.price_per_kg = price_per_kg;
        this.file_blob = file_blob;
        this.measurement = measurement;
    }

    public Product(int produce_id, String uuid, String unique_code, String name, String produce_type, String created_at, String updated_at, String file_name, String description, String price_per_kg, String file_blob) {
        this.produce_id = produce_id;
        this.uuid = uuid;
        this.unique_code = unique_code;
        this.name = name;
        this.produce_type = produce_type;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.file_name = file_name;
        this.description = description;
        this.price_per_kg = price_per_kg;
        this.file_blob = file_blob;
    }

    public Product() {
    }

    public int getProduce_id() {
        return produce_id;
    }

    public void setProduce_id(int produce_id) {
        this.produce_id = produce_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUnique_code() {
        return unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduce_type() {
        return produce_type;
    }

    public void setProduce_type(String produce_type) {
        this.produce_type = produce_type;
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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice_per_kg() {
        return price_per_kg;
    }

    public void setPrice_per_kg(String price_per_kg) {
        this.price_per_kg = price_per_kg;
    }

    public String getFile_blob() {
        return file_blob;
    }

    public void setFile_blob(String file_blob) {
        this.file_blob = file_blob;
    }
}
