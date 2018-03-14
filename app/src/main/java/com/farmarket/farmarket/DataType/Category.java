package com.farmarket.farmarket.DataType;

import java.io.Serializable;

/**
 * Created by admin on 14/03/2018.
 */

public class Category implements Serializable {
    int category_id;
    String name;

    public Category(int category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public Category() {
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
