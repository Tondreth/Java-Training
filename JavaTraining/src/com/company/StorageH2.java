package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StorageH2 extends StorageSelector{

    @Override
    void writeToStorage(Account account) throws SQLException, ClassNotFoundException {

        String myDriver = "org.h2.Driver";
        Class.forName(myDriver);
        Connection connect = DriverManager.getConnection(this.getStorageLocation(), "SA", "");

        PreparedStatement writeToDB = connect.prepareStatement("INSERT " +
                "INTO accounts (" +
                "account_id," +
                "first_name," +
                "last_name," +
                "current_balance," +
                "interest_rate," +
                "account_type," +
                "date_created," +
                "term_date) VALUES (?,?,?,?,?,?,?,?)");

        writeToDB.setInt(1, account.getAccountId());
        writeToDB.setString(2, account.getAccountOwnerFirstName());
        writeToDB.setString(3, account.getAccountOwnerLastName());
        writeToDB.setDouble(4, account.getCurrentBalance());
        writeToDB.setDouble(5, account.getInterestRate());
        writeToDB.setString(6, account.getAccountType());
        writeToDB.setString(7, account.getCurrentDate());
        writeToDB.setString(8, account.getTermDate());

        writeToDB.execute();
        writeToDB.close();

    }
}
