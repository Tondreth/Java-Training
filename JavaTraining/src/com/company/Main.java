package com.company;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Account account1 = new Account(4,
                "Jaina",
                "Proudmoore",
                333,
                7.7F,
                "deposit");

        StorageH2 h2 = new StorageH2();
        h2.setStorageLocation("jdbc:h2:tcp://localhost/~/test");
        account1.writeToStorage(h2);

        Account account2 = new Account(1,
                "Uncle",
                "Sam",
                555,
                2.3F,
                "current");

        StorageJSON json = new StorageJSON();
        json.setStorageLocation("C:\\Users\\Nikolay.Nikolov\\IdeaProjects\\JavaTraining\\storage.json");
        account1.writeToStorage(json);

    }
}
