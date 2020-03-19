package com.company;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Account{

    private int accountNumber;
    private String accountOwnerFirstName;
    private String accountOwnerLastName;
    private double openingBalance;
    private double currentBalance;
    private float interestRate;

    private String type;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    private LocalDateTime getCurrentDate = LocalDateTime.now();
    private String currentDate = formatter.format(getCurrentDate);
    private String openingDate = formatter.format(getCurrentDate);
    private String defaultTermDate = formatter.format(getCurrentDate.plusDays(1));
    private String depositTermDate = formatter.format(getCurrentDate.plusDays(10));

    protected int getAccountNumber() {
        return accountNumber;
    }

    protected void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
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

    protected double getOpeningBalance() {
        return openingBalance;
    }

    protected void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
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

    protected String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    protected String getOpeningDate() {
        return openingDate;
    }

    protected void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    protected String getDefaultTermDate() {
        return defaultTermDate;
    }

    protected void setDefaultTermDate(String defaultTermDate) {
        this.defaultTermDate = defaultTermDate;
    }

    protected String getDepositTermDate() {
        return depositTermDate;
    }

    protected String getCurrentDate() {
        return currentDate;
    }

    protected final String myDriver = "org.h2.Driver";
    protected final String myUrl = "jdbc:h2:tcp://localhost/~/test";

    protected void transaction(int fromAccId,
                               int toAccId,
                               String action,
                               double amount) throws ClassNotFoundException, SQLException {

        ArrayList<String> transaction = new ArrayList<>(Arrays.asList("deposit", "withdraw"));

        if (amount > 0 && transaction.contains(action)) {

            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "SA", "");

            PreparedStatement getAccountInfo = conn.prepareStatement("SELECT * " +
                    "FROM account " +
                    "WHERE account_number_id = " + fromAccId);

            getAccountInfo.execute();

            PreparedStatement addTransaction = conn.prepareStatement("INSERT " +
                    "INTO transactions (" +
                    "sender_id," +
                    "sender_name," +
                    "recipient_id," +
                    "recipient_name," +
                    "action," +
                    "amount," +
                    "date) VALUES (?,?,?,?,?,?,?)");

            PreparedStatement depositOrWithdraw = conn.prepareStatement("UPDATE account " +
                    "SET current_balance = ?" +
                    " WHERE account_number_id = " + fromAccId);

            while (getAccountInfo.getResultSet().next()) {

                addTransaction.setInt(1, getAccountInfo.getResultSet().getInt(1));
                addTransaction.setString(2, getAccountInfo.getResultSet().getString(2)
                        + " " + getAccountInfo.getResultSet().getString(3));
                addTransaction.setInt(3, getAccountInfo.getResultSet().getInt(1));
                addTransaction.setString(4, getAccountInfo.getResultSet().getString(2)
                        + " " + getAccountInfo.getResultSet().getString(3));
                addTransaction.setString(5, action);
                addTransaction.setDouble(6, amount);
                addTransaction.setString(7, getCurrentDate());

                if (getAccountInfo.getResultSet().getString(6).equals("deposit") &&
                        (!getCurrentDate().equals(getAccountInfo.getResultSet().getString(9)))){

                    System.out.println("Deposit accounts can only make transactions on term date.\n" +
                            "Term date for this account is: " +
                            getAccountInfo.getResultSet().getString(9));

                }else{

                    switch (action){

                        case "deposit":
                            depositOrWithdraw.setDouble(1,
                                    getAccountInfo.getResultSet().getDouble(7) + amount);
                            depositOrWithdraw.execute();
                            addTransaction.execute();
                            break;

                        case "withdraw":
                            depositOrWithdraw.setDouble(1,
                                    getAccountInfo.getResultSet().getDouble(7) - amount);
                            depositOrWithdraw.execute();
                            addTransaction.execute();
                            break;

                    }

                }

            }

            conn.close();

        } else {
            System.out.println("Error: Invalid parameters for transaction.");
        }

    }

    protected void createObjectFromDB(int accountId) throws SQLException, ClassNotFoundException {

        Class.forName(myDriver);
        Connection conn = DriverManager.getConnection(myUrl, "SA", "");

        PreparedStatement select = conn.prepareStatement("SELECT * " +
                "FROM account WHERE account_number_id = ?");

        select.setInt(1, accountId);

        try {
            select.execute();
            while(select.getResultSet().next()){

                this.setInterestRate(select.getResultSet().getInt(1));
                this.setAccountOwnerFirstName(select.getResultSet().getString(2));
                this.setAccountOwnerLastName(select.getResultSet().getString(3));
                this.setOpeningBalance(select.getResultSet().getDouble(4));
                this.setInterestRate(select.getResultSet().getFloat(5));

            }
        }catch (Exception e){
            System.out.println("Error: " + e);
        }

        conn.close();

    }

    protected void insertAccountQuery() throws ClassNotFoundException, SQLException {

        Class.forName(myDriver);
        Connection conn = DriverManager.getConnection(myUrl, "SA", "");

        PreparedStatement insert = conn.prepareStatement("INSERT INTO account (" +
                "account_number_id," +
                "first_name," +
                "last_name," +
                "opening_balance," +
                "interest_rate," +
                "account_type," +
                "current_balance," +
                "opening_date," +
                "term_date) VALUES (?,?,?,?,?,?,?,?,?)");

        insert.setInt(1, this.getAccountNumber());
        insert.setString(2, this.getAccountOwnerFirstName());
        insert.setString(3, this.getAccountOwnerLastName());
        insert.setDouble(4, this.getOpeningBalance());
        insert.setFloat(5, this.getInterestRate());
        insert.setString(6, this.getType());
        insert.setDouble(7, this.getCurrentBalance());
        insert.setString(8, this.getOpeningDate());
        insert.setString(9, this.getDefaultTermDate());

        try {
            insert.execute();
            System.out.println("Added a new account for: " +
                    this.getAccountOwnerFirstName() + " " +
                    this.getAccountOwnerLastName());
        }catch (Exception e){
            System.out.println("Error: " + e);
        }

        conn.close();

    }

}
