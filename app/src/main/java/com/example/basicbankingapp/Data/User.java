package com.example.basicbankingapp.Data;

public class User {

    private String name;
    private int accountNumber;
    private String phone;
    private int balance;
    private String email;

    public User() {
    }

    public User(String name, int accountNumber, String phone, int balance, String email) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.phone = phone;
        this.balance = balance;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
