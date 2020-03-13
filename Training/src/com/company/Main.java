package com.company;

public class Main {

    public static void main(String[] args) {

        CurrentAccount newCurrAcc = new CurrentAccount(
            1,
            "Jacob",
            "Stone",
            397.53,
            3.7F
        );

        DepositAccount newDepAcc = new DepositAccount(
            2,
            "Jessica",
            "Pierce",
            737,
            5.3F
        );

        newCurrAcc.deposit(7777977);
        newCurrAcc.withdraw(7777777);

        newDepAcc.deposit(977);
        newDepAcc.withdraw(777);

        System.out.print(newCurrAcc.getAccountInfo() +
                "\n-----------------------------------\n"
                + newDepAcc.getAccountInfo()) ;

        
    } // end of main method

} // end of Main Class
