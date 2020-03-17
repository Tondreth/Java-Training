package com.company;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Account extends Transactions implements Serializable {

    private int accountNumber;
    private String accountOwnerFirstName;
    private String accountOwnerLastName;
    private double openingBalance;
    private double currentBalance;
    private float interestRate;

    protected enum AccountType{
        CURRENT, DEPOSIT, UNDEFINED
    }
    private AccountType accountType;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
    private LocalDateTime getCurrentDate = LocalDateTime.now();
    private String openingDate = formatter.format(getCurrentDate);
    private String defaultTermDate = formatter.format(getCurrentDate.plusDays(1));

    public Account(int accountNumber,
                   String accountOwnerFirstName,
                   String accountOwnerLastName,
                   double openingBalance,
                   float interestRate,
                   AccountType accountType) {

        this.setAccountNumber(accountNumber);
        this.setAccountOwnerFirstName(accountOwnerFirstName);
        this.setAccountOwnerLastName(accountOwnerLastName);
        this.setOpeningBalance(openingBalance);
        this.setInterestRate(interestRate);
        this.accountType = accountType;
        this.currentBalance = getOpeningBalance();
        setOpeningDate(openingDate);
        setDefaultTermDate(defaultTermDate);
    }

    protected final String myDriver = "org.h2.Driver";
    protected final String myUrl = "jdbc:h2:tcp://localhost/~/test";

    protected void selectQuery(int accountId) throws SQLException, ClassNotFoundException {

        Class.forName(myDriver);
        Connection conn = DriverManager.getConnection(myUrl, "SA", "");

        PreparedStatement select = conn.prepareStatement("SELECT * " +
                "FROM account WHERE account_number_id = ?");

        select.setInt(1, accountId);

        try {
            select.execute();
            while(select.getResultSet().next()){
                String firstName = select.getResultSet().getString(2);
                String lastName = select.getResultSet().getString(3);
                System.out.println("Found account: " + firstName + " " +lastName);
            }
        }catch (Exception e){
            System.out.println("Error: " + e);
        }

        conn.close();

    }

    protected void insertQuery() throws ClassNotFoundException, SQLException {

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
        insert.setString(6, this.getAccountType());
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

    public String getAccountType(){
        return accountType.toString().toLowerCase();
    }

    public String getDefaultTermDate() {
        return defaultTermDate;
    }

    public void setDefaultTermDate(String defaultTermDate) {
        this.defaultTermDate = defaultTermDate;
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
