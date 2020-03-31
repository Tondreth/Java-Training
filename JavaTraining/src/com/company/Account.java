package com.company;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account{

    protected int getAccountId() {
        return accountId;
    }

    private void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    protected String getAccountOwnerFirstName() {
        return accountOwnerFirstName;
    }

    private void setAccountOwnerFirstName(String accountOwnerFirstName) {
        this.accountOwnerFirstName = accountOwnerFirstName;
    }

    protected String getAccountOwnerLastName() {
        return accountOwnerLastName;
    }

    private void setAccountOwnerLastName(String accountOwnerLastName) {
        this.accountOwnerLastName = accountOwnerLastName;
    }

    protected double getCurrentBalance() {
        return currentBalance;
    }

    private void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    protected float getInterestRate() {
        return interestRate;
    }

    private void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    protected String getAccountType() {
        return accountType;
    }

    private void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    protected String getCurrentDate() {
        return currentDate;
    }

    private void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    protected String getTermDate() {
        return termDate;
    }

    private void setTermDate(String termDate) {
        this.termDate = termDate;
    }

    private int accountId;
    private String accountOwnerFirstName;
    private String accountOwnerLastName;
    private double currentBalance;
    private float interestRate;
    private String accountType;
    private LocalDateTime getCurrentDate = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String currentDate = formatter.format(getCurrentDate);
    private String termDate = formatter.format(getCurrentDate.plusDays(2));

    public Account(int accountId,
                   String accountOwnerFirstName,
                   String accountOwnerLastName,
                   double currentBalance,
                   float interestRate,
                   String accountType){

        this.setAccountId(accountId);
        this.setAccountOwnerFirstName(accountOwnerFirstName);
        this.setAccountOwnerLastName(accountOwnerLastName);
        this.setCurrentBalance(currentBalance);
        this.setInterestRate(interestRate);
        this.setAccountType(accountType);
        this.setCurrentDate(getCurrentDate());
        this.setTermDate(getTermDate());

    }

    public void writeToStorage(StorageSelector storage) throws SQLException, ClassNotFoundException {
        storage.writeToStorage(this);
    }

}


