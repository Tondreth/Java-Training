package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    private LocalDateTime getCurrentDate = LocalDateTime.now();
    private String currentDate = formatter.format(getCurrentDate);
    private String openingDate = formatter.format(getCurrentDate);
    private String defaultTermDate = formatter.format(getCurrentDate.plusDays(2));
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

    protected final ArrayList<String> transaction =
            new ArrayList<>(Arrays.asList("deposit", "withdraw", "transfer"));


    protected void transaction(int fromAccId,
                               int toAccId,
                               String action,
                               double amount) throws ClassNotFoundException, SQLException {

        if (amount > 0 && transaction.contains(action)) {

            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "SA", "");

            PreparedStatement getAccountInfo = conn.prepareStatement("SELECT * " +
                    "FROM account " +
                    "WHERE account_number_id = " + fromAccId);
            getAccountInfo.execute();

            PreparedStatement getRecipientInfo = conn.prepareStatement("SELECT * " +
                    "FROM account " +
                    "WHERE account_number_id = " + toAccId);
            getRecipientInfo.execute();

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
                    " WHERE account_number_id = ?");

            while (getAccountInfo.getResultSet().next()) {

                depositOrWithdraw.setInt(2, fromAccId);

                if (getAccountInfo.getResultSet().getString(6).equals("deposit") &&
                        (!getCurrentDate().equals(getAccountInfo.getResultSet().getString(9)))){

                    System.out.println("Deposit accounts can only make transactions on term date.\n" +
                            "Term date for this account is: " +
                            getAccountInfo.getResultSet().getString(9));

                }else if(fromAccId == toAccId){

                    addTransaction.setInt(1, getAccountInfo.getResultSet().getInt(1));
                    addTransaction.setString(2,
                            getAccountInfo.getResultSet().getString(2)
                            + " " + getAccountInfo.getResultSet().getString(3));
                    addTransaction.setInt(3, getAccountInfo.getResultSet().getInt(1));
                    addTransaction.setString(4,
                            getAccountInfo.getResultSet().getString(2)
                            + " " + getAccountInfo.getResultSet().getString(3));
                    addTransaction.setString(5, action);
                    addTransaction.setDouble(6, amount);
                    addTransaction.setString(7, getCurrentDate());

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

                } else if (fromAccId != toAccId && action.equals("transfer")){

                    while (getRecipientInfo.getResultSet().next()){

                        depositOrWithdraw.setDouble(1,
                                getAccountInfo.getResultSet().getDouble(7) - amount);
                        depositOrWithdraw.execute();

                        depositOrWithdraw.setInt(2, toAccId);
                        depositOrWithdraw.setDouble(1,
                                (getRecipientInfo.getResultSet().getDouble(7) + amount)
                        + ((getRecipientInfo.getResultSet().getDouble(7) + amount) *
                                        (getRecipientInfo.getResultSet().getDouble(5)/100)));
                        depositOrWithdraw.execute();

                        addTransaction.setInt(1, getAccountInfo.getResultSet().getInt(1));
                        addTransaction.setString(2,
                                getAccountInfo.getResultSet().getString(2)
                                        + " " + getAccountInfo.getResultSet().getString(3));
                        addTransaction.setInt(3, getRecipientInfo.getResultSet().getInt(1));
                        addTransaction.setString(4,
                                getRecipientInfo.getResultSet().getString(2)
                                        + " " + getAccountInfo.getResultSet().getString(3));
                        addTransaction.setString(5, action);
                        addTransaction.setDouble(6, amount);
                        addTransaction.setString(7, getCurrentDate());

                        addTransaction.execute();

                    }

                }

            }

            conn.close();

        } else {
            System.out.println("Error: Invalid parameters for this transaction.");
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

    @SuppressWarnings("unchecked")
    protected JSONObject createJsonObject(){

        JSONObject accountInfo = new JSONObject();

        accountInfo.put("account_number_id" , getAccountNumber());
        accountInfo.put("first_name" , getAccountOwnerFirstName());
        accountInfo.put("last_name" , getAccountOwnerLastName());
        accountInfo.put("opening_balance" , getOpeningBalance());
        accountInfo.put("interest_rate" , getInterestRate());
        accountInfo.put("account_type" , getType());
        accountInfo.put("current_balance" , getCurrentBalance());
        accountInfo.put("opening_date" , getOpeningDate());
        accountInfo.put("term_date" , getDefaultTermDate());

        JSONObject account = new JSONObject();
        account.put("account", accountInfo);

        return account;

    }

    protected final String pathToJsonFile = "C:\\Users\\Nikolay.Nikolov\\IdeaProjects" +
            "\\Training\\database\\accountsInfo.json";
    protected File jsonStorage = new File(pathToJsonFile);
    protected JSONParser jsonParser = new JSONParser();

    @SuppressWarnings("unchecked")
    protected void writeJsonObjectToFile (Object passJsonObject){

        if (jsonStorage.length() == 0){

            JSONArray accountList = new JSONArray();

            accountList.add(passJsonObject);

            try (FileWriter fileWriter = new FileWriter(pathToJsonFile)) {

                fileWriter.write(accountList.toJSONString());
                fileWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            try (FileReader fileReader = new FileReader(pathToJsonFile)){

                Object jsonAccount = jsonParser.parse(fileReader);
                JSONArray accountList = (JSONArray) jsonAccount;

                if (accountList.toString().contains(
                        passJsonObject.toString()
                )){

                    System.out.println("Error: Record already exists.");

                }else{

                    accountList.add(passJsonObject);

                    try (FileWriter fileWriter = new FileWriter(pathToJsonFile)) {

                        fileWriter.write(accountList.toJSONString());
                        fileWriter.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        }

    }

    protected Integer getJsonObjectId(JSONObject accounts){

        JSONObject account = (JSONObject) accounts.get("account");
        return (Integer) account.get("account_number_id");

    }

    protected final String pathToJsonTransactions = "C:\\Users\\Nikolay.Nikolov\\IdeaProjects" +
            "\\Training\\database\\jsonTransactions.json";

    protected File jsonTransactions = new File(pathToJsonTransactions);

    protected JSONArray transactions = new JSONArray();

    @SuppressWarnings("unchecked")
    protected void jsonTransactions(Integer fromAccId, Integer toAccId, String action , double amount){

        if (amount > 0 && transaction.contains(action)){

            try(FileReader fileReader = new FileReader(pathToJsonFile)) {

                Object readJson = jsonParser.parse(fileReader);
                JSONArray listOfAccounts = (JSONArray) readJson;

                JSONArray updatedAccounts = new JSONArray();

                JSONObject transaction = new JSONObject();

                int transactionId = 0;

                for (int i = 0; i < listOfAccounts.size(); i ++){

                    transactionId ++;

                    JSONObject accounts = (JSONObject) listOfAccounts.get(i);
                    JSONObject account = (JSONObject) accounts.get("account");

                    if (fromAccId.equals(toAccId) &&
                            account.get("account_number_id").toString().equals(fromAccId.toString())){

                        switch (action){
                            case "deposit":
                                account.put("current_balance", (
                                        (double)account.get("current_balance") + amount));

                                transaction.put("transaction_id", transactionId);
                                transaction.put("sender_id", account.get("account_number_id"));
                                transaction.put("sender_name", (account.get("first_name").toString()
                                        + " " + account.get("last_name").toString()));
                                transaction.put("recipient_id", account.get("account_number_id"));
                                transaction.put("recipient_name", (account.get("first_name").toString()
                                        + " " + account.get("last_name").toString()));
                                transaction.put("action", action);
                                transaction.put("amount", amount);
                                transaction.put("date", getCurrentDate());

                            break;

                            case "withdraw":
                                account.put("current_balance", (
                                        (double)account.get("current_balance") - amount));

                                transaction.put("transaction_id", transactionId);
                                transaction.put("sender_id", account.get("account_number_id"));
                                transaction.put("sender_name", (account.get("first_name").toString()
                                        + " " + account.get("last_name").toString()));
                                transaction.put("recipient_id", account.get("account_number_id"));
                                transaction.put("recipient_name", (account.get("first_name").toString()
                                        + " " + account.get("last_name").toString()));
                                transaction.put("action", action);
                                transaction.put("amount", amount);
                                transaction.put("date", getCurrentDate());

                            break;
                        }

                    }else if(!fromAccId.equals(toAccId) && action.equals("transfer")) {

                        if (account.get("account_number_id").toString().equals(fromAccId.toString())){

                            account.put("current_balance", (
                                    (double)account.get("current_balance") - amount));

                        }

                        if (account.get("account_number_id").toString().equals(toAccId.toString())){

                            account.put("current_balance", (
                                    (double)account.get("current_balance") + amount));

                        }

                    }

                    JSONObject updatedAccount = new JSONObject();
                    updatedAccount.put("account" , account);

                    updatedAccounts.add(updatedAccount);

                }

                transactions.add(transaction);

                System.out.println(transactions);

                try(FileWriter writeTransaction = new FileWriter(pathToJsonTransactions)){

                    writeTransaction.write(transactions.toJSONString());
                    writeTransaction.flush();

                }catch (IOException e) {
                    e.printStackTrace();
                }


                try(FileWriter fileWriter = new FileWriter(pathToJsonFile)) {

                    fileWriter.write(updatedAccounts.toJSONString());
                    fileWriter.flush();

                }
                catch(IOException e) {
                    e.printStackTrace();
                }

            }catch (IOException | ParseException e){
                e.printStackTrace();
            }

        }else {
            System.out.println("Error: Invalid parameters for this transaction.");
        }

    }

}
