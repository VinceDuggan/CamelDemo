package com.example.cameldemogradle;

public class AccountPayload {
    private int accountNo;
    private String accountName;
    private double amount;


    public AccountPayload() {
    }

    public AccountPayload(int accountNo, String accountName, double amount) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.amount = amount;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return  "accountNo=" + accountNo +
                ", accountName='" + accountName +
                ", amount=" + amount ;
    }
}
