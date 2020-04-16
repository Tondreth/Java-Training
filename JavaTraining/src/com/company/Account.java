package com.company;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account{

    protected int getAccountId() {
        return accountId;
    }

    protected void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    protected String getAccountOwnerFirstName() {
        return accountOwnerFirstName;
    }

    protected void setAccountOwnerFirstName(String accountOwnerFirstName) {
        this.accountOwnerFirstName = accountOwnerFirstName;
    }

    protected String getAccountOwnerLastName() {
        return accountOwnerLastName;
    }

    protected void setAccountOwnerLastName(String accountOwnerLastName) {
        this.accountOwnerLastName = accountOwnerLastName;
    }

    protected double getCurrentBalance() {
        return currentBalance;
    }

    protected void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    protected float getInterestRate() {
        return interestRate;
    }

    protected void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    protected String getAccountType() {
        return accountType;
    }

    protected void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    protected String getCurrentDate() {
        return currentDate;
    }

    protected void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    protected String getTermDate() {
        return termDate;
    }

    protected void setTermDate(String termDate) {
        this.termDate = termDate;
    }

    private int accountId;
    private String accountOwnerFirstName;
    private String accountOwnerLastName;
    private double currentBalance;
    private float interestRate;
    private String accountType;
    private LocalDateTime localDate = LocalDateTime.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String currentDate = formatter.format(localDate);
    private String termDate = formatter.format(localDate.plusDays(2));


    public void writeToStorage(StorageSelector storage) throws SQLException, ClassNotFoundException {

        storage.writeToStorage(this);

    }

    public void initFromStorage(String accountNumber,
                                StorageSelector storage) throws SQLException, ClassNotFoundException {

        storage.initFromStorage(accountNumber, this);

    }

    public void deposit(double amount){

        setCurrentBalance(getCurrentBalance() + amount);

    }

    public void withdraw(double amount){

        setCurrentBalance(getCurrentBalance() - amount);

    }

//    public void getAccInfo(){
//        System.out.println(
//            getAccountId() + "\n" +
//            getAccountOwnerFirstName() + "\n" +
//            getAccountOwnerLastName() + "\n" +
//            getCurrentBalance() + "\n" +
//            getInterestRate() + "\n" +
//            getAccountType() + "\n" +
//            getCurrentDate() + "\n" +
//            getTermDate()
//        );
//    }

}


