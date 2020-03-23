package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
                5,
                "James",
                "Baucher",
                99999,
                7.1F
        );

        DepositAccount depositAccountJessica = new DepositAccount(
                5,
                db.getAccountOwnerFirstName(),
                db.getAccountOwnerLastName(),
                33333,
                9.3F
        );

//        newCurrentAccount.insertAccountQuery();
//        newDepositAccount.insertAccountQuery();
//        depositAccountJessica.insertAccountQuery();

        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(
                "C:\\Users\\Nikolay.Nikolov\\IdeaProjects\\Training\\database\\accountsInfo.json")){

            Object jsonAccount = jsonParser.parse(fileReader);
            JSONArray accountList = (JSONArray) jsonAccount;

            accountList.forEach(account -> db.readAccountsFromFile( (JSONObject) account ));

            accountList.add(depositAccountJessica.createJsonObject());

            try (FileWriter fileWriter = new FileWriter(
                    "C:\\Users\\Nikolay.Nikolov\\IdeaProjects\\Training\\database\\accountsInfo.json")) {

                System.out.println(accountList);
                fileWriter.write(accountList.toJSONString());
                fileWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

//        JSONArray accountsList = new JSONArray();
//        accountsList.add(newCurrentAccount.createJsonObject());
//        accountsList.add(newDepositAccount.createJsonObject());
//        accountsList.add(depositAccountJessica.createJsonObject());

//        try (FileWriter fileWriter = new FileWriter(
//                "C:\\Users\\Nikolay.Nikolov\\IdeaProjects\\Training\\database\\accountsInfo.json" ,
//                true)) {
//
//            System.out.println(accountsList);
//            fileWriter.write(accountsList.toJSONString());
//            fileWriter.flush();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        db.transaction(1,3,"transfer", 357);
//        db.transaction(2,2,"withdraw", 333);

    } // end of main method

} // end of Main Class
