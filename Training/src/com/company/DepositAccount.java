package com.company;

public class DepositAccount extends Account{

    public DepositAccount(int accountNumber,
                          String accountOwnerFirstName,
                          String accountOwnerLastName,
                          double openingBalance,
                          float interestRate) {
        super(accountNumber,
                accountOwnerFirstName,
                accountOwnerLastName,
                openingBalance,
                interestRate,
                AccountType.DEPOSIT);
    }

}
