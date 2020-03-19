package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Account db = new Account();
        db.createObjectFromDB(1);

        CurrentAccount newCurrentAccount = new CurrentAccount(
                1,
                "Jessica",
                "Hey",
                537,
                3.7F
        );

        DepositAccount newDepositAccount = new DepositAccount(
                2,
                db.getAccountOwnerFirstName(),
                db.getAccountOwnerLastName(),
                735,
                9.1F
        );

        newCurrentAccount.insertAccountQuery();
        newDepositAccount.insertAccountQuery();

        db.transaction(1,1,"deposit", 777);
        db.transaction(2,2,"withdraw", 333);



    } // end of main method

} // end of Main Class
