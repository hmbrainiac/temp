package com.farmarket.farmarket.RealmTables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by isaac on 2/18/18.
 */

public class UserViewSettingTable extends RealmObject {
    @PrimaryKey
    int id;
    String viewType;

    public UserViewSettingTable(int id, String viewType) {
        this.id = id;
        this.viewType = viewType;
    }

    public UserViewSettingTable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }
}
