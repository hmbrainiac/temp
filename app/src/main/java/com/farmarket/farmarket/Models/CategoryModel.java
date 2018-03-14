package com.farmarket.farmarket.Models;

/**
 * Created by admin on 14/03/2018.
 */

public class CategoryModel {
    int category_id;
    String name;

    public CategoryModel(int category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public CategoryModel() {
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
