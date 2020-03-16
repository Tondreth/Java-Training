package com.company;

public class UndefinedAccount extends Account {
    public UndefinedAccount(int accountNumber,
                            String accountOwnerFirstName,
                            String accountOwnerLastName,
                            double openingBalance,
                            float interestRate) {
        super(accountNumber,
                accountOwnerFirstName,
                accountOwnerLastName,
                openingBalance,
                interestRate,
                AccountType.UNDEFINED);
    }
}
