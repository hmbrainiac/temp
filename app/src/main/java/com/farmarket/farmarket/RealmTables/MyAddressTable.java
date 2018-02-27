package com.farmarket.farmarket.RealmTables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 27/02/2018.
 */

public class MyAddressTable extends RealmObject {

    @PrimaryKey
    int id;
    String deliveryTown,delivryRegion,residentialAddress,ghana_post_gps;

    public MyAddressTable(int id, String deliveryTown, String delivryRegion, String residentialAddress, String ghana_post_gps) {
        this.id = id;
        this.deliveryTown = deliveryTown;
        this.delivryRegion = delivryRegion;
        this.residentialAddress = residentialAddress;
        this.ghana_post_gps = ghana_post_gps;
    }

    public MyAddressTable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeliveryTown() {
        return deliveryTown;
    }

    public void setDeliveryTown(String deliveryTown) {
        this.deliveryTown = deliveryTown;
    }

    public String getDelivryRegion() {
        return delivryRegion;
    }

    public void setDelivryRegion(String delivryRegion) {
        this.delivryRegion = delivryRegion;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getGhana_post_gps() {
        return ghana_post_gps;
    }

    public void setGhana_post_gps(String ghana_post_gps) {
        this.ghana_post_gps = ghana_post_gps;
    }
}
