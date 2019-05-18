package com.farmarket.farmarket.Models;

/**
 * Created by isaac on 2/17/18.
 */

public class TransactionModel {
/**

 DROP TABLE IF EXISTS `transactions`;
 CREATE TABLE `transactions` (
 `transaction_id` int(255) NOT NULL,
 `reference` varchar(20) NOT NULL,
 `invoice_id` varchar(255) NOT NULL,
 `amount` double(12,2) NOT NULL,
 `amount_after_charge` double(12,2) NOT NULL,
 `transaction_type` enum('Reversal','Deposit','Withdrawal','Payment') NOT NULL,
 `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
 `user_id` int(25) NOT NULL DEFAULT '0',
 `admin_id` int(25) NOT NULL,
 `phone` varchar(15) NOT NULL
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

 */

int transaction_id,invoice_id,user_id,admin_id;
double amount,amount_after_charge;
String reference,transaction_type,phone,created_at,updated_at;
InvoiceModel invoice;

    public TransactionModel(int transaction_id, int invoice_id, int user_id, int admin_id, double amount, double amount_after_charge, String reference, String transaction_type, String phone, String created_at, String updated_at, InvoiceModel invoice) {
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
        this.invoice = invoice;
    }

    public TransactionModel() {
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

    public InvoiceModel getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceModel invoice) {
        this.invoice = invoice;
    }
}
