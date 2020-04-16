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

    private JSONParser jsonParser = new JSONParser();

    @SuppressWarnings("unchecked")
    @Override
    void writeToStorage(Account account) {

        File jsonStorage = new File(this.getStorageLocation());

        JSONObject AccountJson = new JSONObject();
        AccountJson.put("account_id", account.getAccountId());
        AccountJson.put("first_name", account.getAccountOwnerFirstName());
        AccountJson.put("last_name", account.getAccountOwnerLastName());
        AccountJson.put("current_balance", account.getCurrentBalance());
        AccountJson.put("interest_rate", account.getInterestRate());
        AccountJson.put("account_type", account.getAccountType());
        AccountJson.put("date_created", account.getCurrentDate());
        AccountJson.put("term_date", account.getTermDate());

        JSONObject accounts = new JSONObject();
        accounts.put("account", AccountJson);

        JSONObject depositOrWithdraw = new JSONObject();
        depositOrWithdraw.put("type", "deposit");
        depositOrWithdraw.put("sender_id", account.getAccountId());
        depositOrWithdraw.put("recipient_id", account.getAccountId());
        depositOrWithdraw.put("amount", account.getCurrentBalance());
        depositOrWithdraw.put("date", getLocalDateNow());

        JSONObject transaction = new JSONObject();
        transaction.put("transaction", depositOrWithdraw);

        if (jsonStorage.length() == 0) {

            JSONArray accountList = new JSONArray();

            accountList.add(accounts);
            accountList.add(transaction);

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

                if (accountList.toString().contains(AccountJson.get("account_id").toString())){

                    for (int i = 0; i < accountList.size(); i++){

                        JSONObject AllJsonAccounts = (JSONObject) accountList.get(i);
                        JSONObject jsonAccounts = (JSONObject) AllJsonAccounts.get("account");

                        if (jsonAccounts != null &&
                                jsonAccounts.get("account_id").toString().equals(
                                        AccountJson.get("account_id").toString())){

                            double storageBalance = Double.parseDouble(
                                    jsonAccounts.get("current_balance").toString());
                            double objectBalance = Double.parseDouble(
                                    AccountJson.get("current_balance").toString());

                            if (storageBalance < objectBalance){

                                jsonAccounts.put("current_balance", objectBalance);
                                depositOrWithdraw.put("amount", objectBalance - storageBalance);

                            }else if (storageBalance > objectBalance){

                                jsonAccounts.put("current_balance", objectBalance);
                                depositOrWithdraw.put("amount", storageBalance - objectBalance);
                                depositOrWithdraw.put("type", "withdraw");

                            }else if (storageBalance == objectBalance){

                                System.out.println("ERROR: Record already exists.");

                            }

                            accountList.add(transaction);

                            try (FileWriter fileWriter = new FileWriter(this.getStorageLocation())) {

                                fileWriter.write(accountList.toJSONString());
                                fileWriter.flush();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                }else{

                    accountList.add(accounts);
                    accountList.add(transaction);

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

    @Override
    void initFromStorage(String accountNumber, Account account) {

        try(FileReader fileReader = new FileReader(this.getStorageLocation())){

            Object AllJsonAccounts = jsonParser.parse(fileReader);
            JSONArray accountList = (JSONArray) AllJsonAccounts;


            for(int i = 0; i < accountList.size(); i++) {

                JSONObject jsonAccounts = (JSONObject) accountList.get(i);
                JSONObject jsonAccount = (JSONObject) jsonAccounts.get("account");

                if (jsonAccount != null &&
                        jsonAccount.get("account_id").toString().equals(accountNumber)) {

                    account.setAccountId(Integer.parseInt(jsonAccount.get("account_id").toString()));
                    account.setAccountOwnerFirstName(jsonAccount.get("first_name").toString());
                    account.setAccountOwnerLastName(jsonAccount.get("last_name").toString());
                    account.setCurrentBalance(Double.parseDouble(
                            jsonAccount.get("current_balance").toString()));
                    account.setInterestRate(Float.parseFloat(
                            jsonAccount.get("interest_rate").toString()));
                    account.setAccountType(jsonAccount.get("account_type").toString());
                    account.setCurrentDate(jsonAccount.get("date_created").toString());
                    account.setTermDate(jsonAccount.get("term_date").toString());

                }

            }

        }catch (IOException | ParseException e){
            e.printStackTrace();
        }

    }


}
