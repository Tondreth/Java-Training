package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        CurrentAccount newCurrentAccount = new CurrentAccount(
                1,
                "Jaina",
                "Proudmoore",
                379.3,
                3.7F
        );

        DepositAccount newDepositAccount = new DepositAccount(
                2,
                "John",
                "Doe",
                375,
                9.7F
        );

        newDepositAccount.insertQuery();
        newCurrentAccount.insertQuery();
        newCurrentAccount.selectQuery(2);

    } // end of main method

} // end of Main Class
