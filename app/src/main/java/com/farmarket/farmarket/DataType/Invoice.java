package com.farmarket.farmarket.DataType;

import java.io.Serializable;

/**
 * Created by admin on 27/02/2018.
 */

public class Invoice  implements Serializable {
    int invoice_id;
    String uuid,unique_code,amount,created_at,updated_at,reference,payment_status,amount_after_charge;

    public Invoice(int invoice_id, String uuid, String unique_code, String amount, String created_at, String updated_at, String reference, String payment_status, String amount_after_charge) {
        this.invoice_id = invoice_id;
        this.uuid = uuid;
        this.unique_code = unique_code;
        this.amount = amount;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.reference = reference;
        this.payment_status = payment_status;
        this.amount_after_charge = amount_after_charge;
    }

    public Invoice() {
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getAmount_after_charge() {
        return amount_after_charge;
    }

    public void setAmount_after_charge(String amount_after_charge) {
        this.amount_after_charge = amount_after_charge;
    }
}
