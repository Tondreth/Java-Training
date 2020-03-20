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
                3537,
                3.7F
        );

        DepositAccount newDepositAccount = new DepositAccount(
                2,
                "James",
                "Baucher",
                0,
                9.1F
        );

        DepositAccount depositAccountJessica = new DepositAccount(
                3,
                db.getAccountOwnerFirstName(),
                db.getAccountOwnerLastName(),
                357,
                9.3F
        );

        newCurrentAccount.insertAccountQuery();
        newDepositAccount.insertAccountQuery();
        depositAccountJessica.insertAccountQuery();

        newCurrentAccount.writeToFile();
        newDepositAccount.writeToFile();
        depositAccountJessica.writeToFile();

        db.transaction(1,3,"transfer", 357);
//        db.transaction(2,2,"withdraw", 333);

    } // end of main method

} // end of Main Class
