package com.company;

public class CurrentAccount extends Account{

    public CurrentAccount(int accountNumber,
                          String accountOwnerFirstName,
                          String accountOwnerLastName,
                          double openingBalance,
                          float interestRate) {
        super(accountNumber,
                accountOwnerFirstName,
                accountOwnerLastName,
                openingBalance,
                interestRate,
                AccountType.CURRENT);
    }

}
