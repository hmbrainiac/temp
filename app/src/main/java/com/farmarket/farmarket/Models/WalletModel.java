package com.farmarket.farmarket.Models;

import com.farmarket.farmarket.DataView.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaac on 3/17/18.
 */

public class WalletModel {
    int user_id,wallet_id;
    double balance;
    List<TransactionModel> transactions;

    public WalletModel(int user_id, int wallet_id, double balance, List<TransactionModel> transactions) {
        this.user_id = user_id;
        this.wallet_id = wallet_id;
        this.balance = balance;
        this.transactions = transactions;
    }

    public List<TransactionModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionModel> transactions) {
        this.transactions = transactions;
    }

    public WalletModel(int user_id, int wallet_id, double balance) {
        this.user_id = user_id;
        this.wallet_id = wallet_id;
        this.balance = balance;
    }

    public WalletModel() {
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
}
