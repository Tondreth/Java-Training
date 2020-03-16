package com.company;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Account implements Serializable {

    private int accountNumber;
    private String accountOwnerFirstName;
    private String accountOwnerLastName;
    private double openingBalance;
    private double currentBalance;
    private float interestRate;

    private Date getCurrentDate = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    private String openingDate = dateFormat.format(getCurrentDate);

    protected enum AccountType{
        CURRENT, DEPOSIT, UNDEFINED
    }
    private AccountType accountType;

    public Account(int accountNumber,
                   String accountOwnerFirstName,
                   String accountOwnerLastName,
                   double openingBalance,
                   float interestRate,
                   AccountType accountType){

        this.setAccountNumber(accountNumber);
        this.setAccountOwnerFirstName(accountOwnerFirstName);
        this.setAccountOwnerLastName(accountOwnerLastName);
        this.setOpeningBalance(openingBalance);
        this.setInterestRate(interestRate);
        this.accountType = accountType;
        this.currentBalance = getOpeningBalance();
        setOpeningDate(openingDate);
    }

    public void deposit (double depositAmount){
        this.currentBalance = getCurrentBalance() + depositAmount;
    }

    public void withdraw (double withdrawAmount){
        this.currentBalance = getCurrentBalance() - withdrawAmount;
    }

    public String getAccountType(){
        return accountType.toString().toLowerCase();
    }

    public String getOpeningDate() {
        return openingDate;
    }

    private void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountOwnerFirstName() {
        return accountOwnerFirstName;
    }

    public void setAccountOwnerFirstName(String accountOwnerFirstName) {
        this.accountOwnerFirstName = accountOwnerFirstName;
    }

    public String getAccountOwnerLastName() {
        return accountOwnerLastName;
    }

    public void setAccountOwnerLastName(String accountOwnerLastName) {
        this.accountOwnerLastName = accountOwnerLastName;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    private void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

}
