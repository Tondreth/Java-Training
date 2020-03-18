package com.company;

public class DepositAccount extends Account{

    public DepositAccount(int accountNumber,
                          String accountOwnerFirstName,
                          String accountOwnerLastName,
                          double openingBalance,
                          float interestRate) {

        this.setAccountNumber(accountNumber);
        this.setAccountOwnerFirstName(accountOwnerFirstName);
        this.setAccountOwnerLastName(accountOwnerLastName);
        this.setOpeningBalance(openingBalance);
        this.setInterestRate(interestRate);
        this.setType("deposit");
        this.setCurrentBalance(getOpeningBalance());
        this.setOpeningDate(getOpeningDate());
        this.setDefaultTermDate(getDefaultTermDate());

    }

}
