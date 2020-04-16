package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        StorageH2 h2 = new StorageH2();
        h2.setStorageLocation("jdbc:h2:tcp://localhost/~/test");

        StorageJSON json = new StorageJSON();
        json.setStorageLocation("C:\\Users\\Nikolay.Nikolov\\IdeaProjects\\JavaTraining\\storage.json");

        Account test = new Account();
        test.setAccountId(1);
        test.setAccountOwnerFirstName("Jessica");
        test.setAccountOwnerLastName("Stones");
        test.setAccountType("deposit");
        test.setCurrentBalance(3597);
        test.setInterestRate(3.9F);
        test.setCurrentDate(test.getCurrentDate());
        test.setTermDate(test.getTermDate());

        test.writeToStorage(h2);

        test.initFromStorage("1", h2);
        test.writeToStorage(json);

        test.withdraw(1111);
        test.writeToStorage(json);

        test.deposit(357);
        test.writeToStorage(json);


//        test.getAccInfo();


    }
}
