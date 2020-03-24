package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Account dataOperations = new Account();
        dataOperations.createObjectFromDB(1);

        CurrentAccount newCurrentAccount = new CurrentAccount(
                1,
                "Jessica",
                "Hey",
                3537,
                3.7F
        );

        DepositAccount newDepositAccount = new DepositAccount(
                5,
                "James",
                "Baucher",
                99999,
                7.1F
        );

        DepositAccount depositAccountJessica = new DepositAccount(
                77,
                "Librarian",
                "Joshua",
                11111,
                9.3F
        );

//        newCurrentAccount.insertAccountQuery();
//        newDepositAccount.insertAccountQuery();
//        depositAccountJessica.insertAccountQuery();

//        dataOperations.transaction(1,2, "transfer", 3100);

        dataOperations.writeJsonObjectToFile(depositAccountJessica.createJsonObject());

        dataOperations.jsonTransactions();

//        System.out.print(dataOperations.getJsonObjectValue(
//                newCurrentAccount.createJsonObject()).get("current_balance"));

    } // end of main method

} // end of Main Class
