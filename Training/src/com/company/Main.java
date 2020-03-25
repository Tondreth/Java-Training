package com.company;

import org.json.simple.JSONObject;

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
                2,
                "James",
                "Baucher",
                357,
                7.1F
        );

        DepositAccount newDepositAccount2 = new DepositAccount(
                3,
                "Librarian",
                "Joshua",
                753,
                9.3F
        );

//        newCurrentAccount.insertAccountQuery();
//        newDepositAccount.insertAccountQuery();
//        depositAccountJessica.insertAccountQuery();

//        dataOperations.transaction(1,2, "transfer", 3100);


//        dataOperations.writeJsonObjectToFile(newCurrentAccount.createJsonObject());
//        dataOperations.writeJsonObjectToFile(newDepositAccount.createJsonObject());
//        dataOperations.writeJsonObjectToFile(newDepositAccount2.createJsonObject());

        dataOperations.jsonTransactions(1, 3, "transfer", 777);
//        dataOperations.jsonTransactions(1, 1, "deposit" , 333);
//        dataOperations.jsonTransactions(3, 3, "withdraw" , 333);

//        System.out.println(dataOperations.getJsonObjectId(
//                depositAccountJessica.createJsonObject()));


    } // end of main method

} // end of Main Class
