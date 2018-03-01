package com.farmarket.farmarket.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by isaac on 2/17/18.
 */

public class OrderModel {
    int order_id,user_id,delivery_id;
    String uuid,created_at,updated_at,expected_delivery,status,invoice_id,delivery_lon,delivery_lat,delivery_gh_post_code,phone,email,name,cart_status,deliveryTown,delivryRegion,residentialAddress,accountNumber,accountNetwork,accountToken,unique_code,created;
    ArrayList<OrderDetailModel> details;
    InvoiceModel invoices;

    public OrderModel(int order_id, int user_id, int delivery_id, String uuid, String created_at, String updated_at, String expected_delivery, String status, String invoice_id, String delivery_lon, String delivery_lat, String delivery_gh_post_code, String phone, String email, String name, String cart_status, String deliveryTown, String delivryRegion, String residentialAddress, String accountNumber, String accountNetwork, String accountToken, ArrayList<OrderDetailModel> details, InvoiceModel invoices) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.delivery_id = delivery_id;
        this.uuid = uuid;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.expected_delivery = expected_delivery;
        this.status = status;
        this.invoice_id = invoice_id;
        this.delivery_lon = delivery_lon;
        this.delivery_lat = delivery_lat;
        this.delivery_gh_post_code = delivery_gh_post_code;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.cart_status = cart_status;
        this.deliveryTown = deliveryTown;
        this.delivryRegion = delivryRegion;
        this.residentialAddress = residentialAddress;
        this.accountNumber = accountNumber;
        this.accountNetwork = accountNetwork;
        this.accountToken = accountToken;
        this.details = details;
        this.invoices = invoices;
    }

    public ArrayList<OrderDetailModel> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OrderDetailModel> details) {
        this.details = details;
    }

    public InvoiceModel getInvoices() {
        return invoices;
    }

    public void setInvoices(InvoiceModel invoices) {
        this.invoices = invoices;
    }

    public String getUnique_code() {
        return unique_code;
    }

    public void setUnique_code(String unique_code) {
        this.unique_code = unique_code;
    }

    public OrderModel(int order_id, int user_id, int delivery_id, String uuid, String created_at, String updated_at, String expected_delivery, String status, String invoice_id, String delivery_lon, String delivery_lat, String delivery_gh_post_code, String phone, String email, String name, String cart_status, String deliveryTown, String delivryRegion, String residentialAddress, String accountNumber, String accountNetwork, String accountToken, String unique_code, ArrayList<OrderDetailModel> details, InvoiceModel invoices) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.delivery_id = delivery_id;
        this.uuid = uuid;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.expected_delivery = expected_delivery;
        this.status = status;
        this.invoice_id = invoice_id;
        this.delivery_lon = delivery_lon;
        this.delivery_lat = delivery_lat;
        this.delivery_gh_post_code = delivery_gh_post_code;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.cart_status = cart_status;
        this.deliveryTown = deliveryTown;
        this.delivryRegion = delivryRegion;
        this.residentialAddress = residentialAddress;
        this.accountNumber = accountNumber;
        this.accountNetwork = accountNetwork;
        this.accountToken = accountToken;
        this.unique_code = unique_code;
        this.details = details;
        this.invoices = invoices;
    }

    public OrderModel(int order_id, int user_id, int delivery_id, String uuid, String created_at, String updated_at, String expected_delivery, String status, String invoice_id, String delivery_lon, String delivery_lat, String delivery_gh_post_code, String phone, String email, String name, String cart_status, String deliveryTown, String delivryRegion, String residentialAddress, String accountNumber, String accountNetwork, String accountToken) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.delivery_id = delivery_id;
        this.uuid = uuid;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.expected_delivery = expected_delivery;
        this.status = status;
        this.invoice_id = invoice_id;
        this.delivery_lon = delivery_lon;
        this.delivery_lat = delivery_lat;
        this.delivery_gh_post_code = delivery_gh_post_code;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.cart_status = cart_status;
        this.deliveryTown = deliveryTown;
        this.delivryRegion = delivryRegion;
        this.residentialAddress = residentialAddress;
        this.accountNumber = accountNumber;
        this.accountNetwork = accountNetwork;
        this.accountToken = accountToken;
    }

    public OrderModel() {
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNetwork() {
        return accountNetwork;
    }

    public void setAccountNetwork(String accountNetwork) {
        this.accountNetwork = accountNetwork;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }

    public JSONObject toJSon()
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("order_id",this.order_id);
            jsonObject.put("user_id",this.user_id);
            jsonObject.put("delivery_id",this.delivery_id);
            jsonObject.put("uuid",this.uuid);
            jsonObject.put("expected_delivery",this.expected_delivery);
            jsonObject.put("status",this.status);
            jsonObject.put("invoice_id",this.invoice_id);
            jsonObject.put("delivery_lon",this.delivery_lon);
            jsonObject.put("delivery_lat",this.delivery_lat);
            jsonObject.put("delivery_gh_post_code",this.delivery_gh_post_code);
            jsonObject.put("phone",this.phone);
            jsonObject.put("email",this.email);
            jsonObject.put("name",this.name);
            jsonObject.put("cart_status",this.cart_status);
            jsonObject.put("deliveryTown",this.deliveryTown);
            jsonObject.put("delivryRegion",this.delivryRegion);
            jsonObject.put("residentialAddress",this.residentialAddress);
            jsonObject.put("accountNumber",this.accountNumber);
            jsonObject.put("accountNetwork",this.accountNetwork);
            jsonObject.put("accountToken",this.accountToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;

    }

    public OrderModel(int order_id, int user_id, int delivery_id, String uuid, String created_at, String updated_at, String expected_delivery, String status, String invoice_id, String delivery_lon, String delivery_lat, String delivery_gh_post_code, String phone, String email, String name, String cart_status, String deliveryTown, String delivryRegion, String residentialAddress, String accountNumber, String accountNetwork, String accountToken, String unique_code, String created, ArrayList<OrderDetailModel> details, InvoiceModel invoices) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.delivery_id = delivery_id;
        this.uuid = uuid;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.expected_delivery = expected_delivery;
        this.status = status;
        this.invoice_id = invoice_id;
        this.delivery_lon = delivery_lon;
        this.delivery_lat = delivery_lat;
        this.delivery_gh_post_code = delivery_gh_post_code;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.cart_status = cart_status;
        this.deliveryTown = deliveryTown;
        this.delivryRegion = delivryRegion;
        this.residentialAddress = residentialAddress;
        this.accountNumber = accountNumber;
        this.accountNetwork = accountNetwork;
        this.accountToken = accountToken;
        this.unique_code = unique_code;
        this.created = created;
        this.details = details;
        this.invoices = invoices;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
