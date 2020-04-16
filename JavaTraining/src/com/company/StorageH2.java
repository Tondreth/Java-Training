package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StorageH2 extends StorageSelector{

    private final String myDriver = "org.h2.Driver";

    @Override
    void writeToStorage(Account account) throws SQLException, ClassNotFoundException {

        Class.forName(myDriver);
        Connection connect = DriverManager.getConnection(
                this.getStorageLocation(), "SA", "");

        PreparedStatement checkExisting = connect.prepareStatement("SELECT * " +
                "FROM accounts WHERE account_id = " + account.getAccountId());

        checkExisting.execute();

        if (checkExisting.getResultSet().next()){

            PreparedStatement depositOrWithdraw = connect.prepareStatement("INSERT " +
                    "INTO transactions (" +
                            "type, " +
                            "sender_id, " +
                            "recipient_id, " +
                            "amount, " +
                            "date) VALUES (?,?,?,?,?)");

            depositOrWithdraw.setInt(2, account.getAccountId());
            depositOrWithdraw.setInt(3, account.getAccountId());
            depositOrWithdraw.setString(5, getLocalDateNow());

            if (checkExisting.getResultSet().getDouble(4) > account.getCurrentBalance()){

                depositOrWithdraw.setString(1, "withdraw");
                depositOrWithdraw.setDouble(4,
                        checkExisting.getResultSet().getDouble(4) -
                                account.getCurrentBalance());
                depositOrWithdraw.execute();

            }else if (checkExisting.getResultSet().getDouble(4) < account.getCurrentBalance()){

                depositOrWithdraw.setString(1, "deposit");
                depositOrWithdraw.setDouble(4, account.getCurrentBalance() -
                        checkExisting.getResultSet().getDouble(4));
                depositOrWithdraw.execute();

            }

            PreparedStatement updateDB = connect.prepareStatement("UPDATE accounts " +
                    "SET current_balance = " + (account.getCurrentBalance() +
                            account.getCurrentBalance() * (account.getInterestRate() / 100)) +
                    " WHERE account_id = " + account.getAccountId());

            updateDB.execute();
            connect.close();

        }else{

            PreparedStatement writeToDB = connect.prepareStatement("INSERT " +
                    "INTO accounts VALUES (?,?,?,?,?,?,?,?)");

            writeToDB.setInt(1, account.getAccountId());
            writeToDB.setString(2, account.getAccountOwnerFirstName());
            writeToDB.setString(3, account.getAccountOwnerLastName());
            writeToDB.setDouble(4, account.getCurrentBalance()
                    + (account.getCurrentBalance() * (account.getInterestRate()/100)));
            writeToDB.setDouble(5, account.getInterestRate());
            writeToDB.setString(6, account.getAccountType());
            writeToDB.setString(7, account.getCurrentDate());
            writeToDB.setString(8, account.getTermDate());

            writeToDB.execute();

            PreparedStatement initialDeposit = connect.prepareStatement("INSERT " +
                    "INTO transactions (" +
                    "type, " +
                    "sender_id, " +
                    "recipient_id, " +
                    "amount, " +
                    "date) VALUES (?,?,?,?,?)");

            initialDeposit.setString(1, "deposit");
            initialDeposit.setInt(2, account.getAccountId());
            initialDeposit.setInt(3, account.getAccountId());
            initialDeposit.setDouble(4, account.getCurrentBalance());
            initialDeposit.setString(5, account.getCurrentDate());

            initialDeposit.execute();

            connect.close();

        }

    }

    @Override
    public void initFromStorage(String accountNumber,
                            Account account) throws SQLException, ClassNotFoundException {

        Class.forName(myDriver);
        Connection connect = DriverManager.getConnection(this.getStorageLocation(), "SA", "");

        PreparedStatement initAccount = connect.prepareStatement("SELECT * " +
                "FROM accounts " +
                "WHERE account_id = ?");

        initAccount.setInt(1, Integer.parseInt(accountNumber));

        initAccount.execute();

        while (initAccount.getResultSet().next()){
            account.setAccountId(initAccount.getResultSet().getInt(1));
            account.setAccountOwnerFirstName(initAccount.getResultSet().getString(2));
            account.setAccountOwnerLastName(initAccount.getResultSet().getString(3));
            account.setCurrentBalance(initAccount.getResultSet().getDouble(4));
            account.setInterestRate(initAccount.getResultSet().getFloat(5));
            account.setAccountType(initAccount.getResultSet().getString(6));
            account.setCurrentDate(initAccount.getResultSet().getString(7));
            account.setTermDate(initAccount.getResultSet().getString(8));
        }

        connect.close();

    }

}
