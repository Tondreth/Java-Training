package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StorageJSON extends StorageSelector{

    @SuppressWarnings("unchecked")
    @Override
    void writeToStorage(Account account) {

        File jsonStorage = new File(this.getStorageLocation());
        JSONParser jsonParser = new JSONParser();

        JSONObject newAccount = new JSONObject();
        newAccount.put("account_id", account.getAccountId());
        newAccount.put("first_name", account.getAccountOwnerFirstName());
        newAccount.put("last_name", account.getAccountOwnerLastName());
        newAccount.put("current_balance", account.getCurrentBalance());
        newAccount.put("interest_rate", account.getInterestRate());
        newAccount.put("account_type", account.getAccountType());
        newAccount.put("date_created", account.getCurrentDate());
        newAccount.put("term_date", account.getTermDate());

        if (jsonStorage.length() == 0) {

            JSONArray accountList = new JSONArray();

            accountList.add(newAccount);

            try (FileWriter fileWriter = new FileWriter(this.getStorageLocation())) {

                fileWriter.write(accountList.toJSONString());
                fileWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            try (FileReader fileReader = new FileReader(this.getStorageLocation())){

                Object jsonAccount = jsonParser.parse(fileReader);
                JSONArray accountList = (JSONArray) jsonAccount;

                if (accountList.toString().contains(
                        newAccount.toString()
                )){

                    System.out.println("Error: Record already exists.");

                }else{

                    accountList.add(newAccount);

                    try (FileWriter fileWriter = new FileWriter(this.getStorageLocation())) {

                        fileWriter.write(accountList.toJSONString());
                        fileWriter.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        }

    }

}
