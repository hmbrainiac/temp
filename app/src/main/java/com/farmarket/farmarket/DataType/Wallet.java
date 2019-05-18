package com.farmarket.farmarket.DataType;

import java.util.List;

/**
 * Created by isaac on 3/17/18.
 */

public class Wallet {
    int user_id,wallet_id;
    double balance;
    List<Transaction> transactions;

    public Wallet(int user_id, int wallet_id, double balance, List<Transaction> transactions) {
        this.user_id = user_id;
        this.wallet_id = wallet_id;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Wallet() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(int wallet_id) {
        this.wallet_id = wallet_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
