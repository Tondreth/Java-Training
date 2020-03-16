package com.company;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String myDriver = "org.h2.Driver";
        String myUrl = "jdbc:h2:tcp://localhost/~/test";
        Class.forName(myDriver);
        Connection conn = DriverManager.getConnection(myUrl, "SA", "");

        CurrentAccount newAccount = new CurrentAccount(
                7,
                "oiLuisa",
                "Bones",
                775,
                3.3F
        );

        String addNewAccountQuery = "INSERT INTO account (" +
            "account_number_id," +
            "first_name," +
            "last_name," +
            "opening_balance," +
            "interest_rate," +
            "account_type," +
            "current_balance," +
            "opening_date) VALUES ("
                + newAccount.getAccountNumber() + ","
                + "'"+newAccount.getAccountOwnerFirstName()+"',"
                + "'"+newAccount.getAccountOwnerLastName()+"',"
                + newAccount.getOpeningBalance() + ","
                + newAccount.getInterestRate() + ","
                + "'"+newAccount.getAccountType()+"',"
                + newAccount.getCurrentBalance() + ","
                + "'"+newAccount.getOpeningDate()+"')";

        Statement st = conn.createStatement();

        st.execute(addNewAccountQuery);


    } // end of main method

} // end of Main Class
