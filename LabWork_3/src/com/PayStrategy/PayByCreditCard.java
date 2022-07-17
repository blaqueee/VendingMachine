package com.PayStrategy;

import com.VendingMachine;

import java.util.Scanner;

public class PayByCreditCard implements PayStrategy {
    @Override
    public void putMoney(VendingMachine vendingMachine) {
        long cardNumber = askCardNumber();
        String password = askPassword();
        int value = askMoney();
        vendingMachine.setBalance(vendingMachine.getBalance() + value);

        System.out.println("\nTransaction has successfully passed!\n");
    }

    private long askCardNumber() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter number of your credit card (16 numbers): ");
            String strNumber = sc.nextLine().replaceAll(" ", "");
            try {
                return checkNumber(strNumber);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private long checkNumber(String str) {
        if (str.equals(""))
            throw new RuntimeException("\nNumber of credit card can't be empty!\n");
        if (str.length() != 16)
            throw new RuntimeException("\nLength of number must be 16!\n");
        return Long.parseLong(str);
    }

    private String askPassword() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter password (8 or more symbols): ");
            String password = sc.nextLine().replaceAll("\\s+", "");
            try {
                return checkPassword(password);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String checkPassword(String password) {
        if (password.equals(""))
            throw new RuntimeException("\nPassword field can't be empty!\n");
        if (password.length() < 8)
            throw new RuntimeException("\nLength of password must be 8 or more!\n");
        return password;
    }

    private int askMoney() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter money: ");
            String value = sc.nextLine().replaceAll("\\s+", "");
            try {
                return checkMoney(value);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int checkMoney(String value) {
        if (value.equals(""))
            throw new RuntimeException("\nYou can't put empty value!\n");
        int money = Integer.parseInt(value);
        if (money < 0)
            throw new RuntimeException("\nThe value of money can't be zero or less!\n");
        return money;
    }
}
