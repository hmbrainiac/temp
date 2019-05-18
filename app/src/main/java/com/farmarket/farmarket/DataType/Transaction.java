package com.farmarket.farmarket.DataType;

import com.farmarket.farmarket.Models.InvoiceModel;

import java.io.Serializable;

/**
 * Created by admin on 27/02/2018.
 */

public class Transaction  implements Serializable {
    String uuid,unique_code,payment_status;
    InvoiceModel invoice;

    int transaction_id,invoice_id,user_id,admin_id;
    double amount,amount_after_charge;
    String reference,transaction_type,phone,created_at,updated_at;

    public Transaction(String uuid, String unique_code, String payment_status, InvoiceModel invoice, int transaction_id, int invoice_id, int user_id, int admin_id, double amount, double amount_after_charge, String reference, String transaction_type, String phone, String created_at, String updated_at) {
        this.uuid = uuid;
        this.unique_code = unique_code;
        this.payment_status = payment_status;
        this.invoice = invoice;
        this.transaction_id = transaction_id;
        this.invoice_id = invoice_id;
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.amount = amount;
        this.amount_after_charge = amount_after_charge;
        this.reference = reference;
        this.transaction_type = transaction_type;
        this.phone = phone;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Transaction() {
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

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public InvoiceModel getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceModel invoice) {
        this.invoice = invoice;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount_after_charge() {
        return amount_after_charge;
    }

    public void setAmount_after_charge(double amount_after_charge) {
        this.amount_after_charge = amount_after_charge;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
