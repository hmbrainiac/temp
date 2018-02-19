package com.farmarket.farmarket.Models;

import java.io.Serializable;

/**
 * Created by isaac on 2/17/18.
 */

public class UserModel implements Serializable {
    //`users`(`user_id`, `uuid`, `unique_code`, `phone`, `email`, `verification_code`, `forgot_code`, `created_at`, `updated_at`, `firstname`, `lastname`, `username`, `user_type`, `user_region`)
    int server_id,user_id,responseCode;
    String uuid,unique_code,phone,email,verification_code,forgot_code,created_at,updated_at,firstname,lastname,username,user_type,user_region;

    public UserModel(int server_id, int user_id, int responseCode, String uuid, String unique_code, String phone, String email, String verification_code, String forgot_code, String created_at, String updated_at, String firstname, String lastname, String username, String user_type, String user_region) {
        this.server_id = server_id;
        this.user_id = user_id;
        this.responseCode = responseCode;
        this.uuid = uuid;
        this.unique_code = unique_code;
        this.phone = phone;
        this.email = email;
        this.verification_code = verification_code;
        this.forgot_code = forgot_code;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.user_type = user_type;
        this.user_region = user_region;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public UserModel(int server_id, int user_id, String uuid, String unique_code, String phone, String email, String verification_code, String forgot_code, String created_at, String updated_at, String firstname, String lastname, String username, String user_type, String user_region) {
        this.server_id = server_id;
        this.user_id = user_id;
        this.uuid = uuid;
        this.unique_code = unique_code;
        this.phone = phone;
        this.email = email;
        this.verification_code = verification_code;
        this.forgot_code = forgot_code;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.user_type = user_type;
        this.user_region = user_region;
    }

    public UserModel() {
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public String getForgot_code() {
        return forgot_code;
    }

    public void setForgot_code(String forgot_code) {
        this.forgot_code = forgot_code;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_region() {
        return user_region;
    }

    public void setUser_region(String user_region) {
        this.user_region = user_region;
    }
}
