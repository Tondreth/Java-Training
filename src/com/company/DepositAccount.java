package com.company;

public class DepositAccount extends Account{

    private String termDate;

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

    @Override
    public void deposit(double depositAmount) {
        super.deposit(depositAmount);
    }

    @Override
    public void withdraw(double withdrawAmount) {
        super.withdraw(withdrawAmount);
    }
}
