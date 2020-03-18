package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Account db = new Account();
        db.createObjectFromDB(1);

        CurrentAccount newCurrentAccount = new CurrentAccount(
                11,
                db.getAccountOwnerFirstName(),
                "Hey",
                db.getOpeningBalance(),
                db.getInterestRate()
        );

        DepositAccount newDepositAccount = new DepositAccount(
                10,
                "Jack",
                db.getAccountOwnerLastName(),
                db.getOpeningBalance(),
                9.7F
        );

//        newCurrentAccount.deposit(777);

        newDepositAccount.insertQuery();
        newCurrentAccount.insertQuery();




    } // end of main method

} // end of Main Class
