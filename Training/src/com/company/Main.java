package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Account dataOperations = new Account();
        dataOperations.createObjectFromDB(2);

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
                "Joshua",
                dataOperations.getAccountOwnerLastName(),
                dataOperations.getOpeningBalance(),
                9.3F
        );

        newCurrentAccount.insertAccountQuery();
        newDepositAccount.insertAccountQuery();
        newDepositAccount2.insertAccountQuery();

        dataOperations.transaction(1,2, "transfer", 777);
        dataOperations.transaction(2,2, "withdraw", 555);
        dataOperations.transaction(3,3, "deposit", 333);

        dataOperations.writeJsonObjectToFile(newCurrentAccount.createJsonObject());
        dataOperations.writeJsonObjectToFile(newDepositAccount.createJsonObject());
        dataOperations.writeJsonObjectToFile(newDepositAccount2.createJsonObject());

        dataOperations.jsonTransactions(1, 2, "transfer", 777);
        dataOperations.jsonTransactions(2, 2, "withdraw" , 555);
        dataOperations.jsonTransactions(3, 3, "deposit" , 333);


    }

}
