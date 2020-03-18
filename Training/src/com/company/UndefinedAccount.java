package com.company;

public class UndefinedAccount extends Account {

    public UndefinedAccount(int accountNumber,
                            String accountOwnerFirstName,
                            String accountOwnerLastName,
                            double openingBalance,
                            float interestRate) {

        this.setAccountNumber(accountNumber);
        this.setAccountOwnerFirstName(accountOwnerFirstName);
        this.setAccountOwnerLastName(accountOwnerLastName);
        this.setOpeningBalance(openingBalance);
        this.setInterestRate(interestRate);
        this.setType("undefined");
        this.setCurrentBalance(getOpeningBalance());
        this.setOpeningDate(getOpeningDate());
        this.setDefaultTermDate(getDefaultTermDate());

    }

}
