package com.PayStrategy;

import com.MoneyTemplate;
import com.VendingMachine;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PayByRealMoney implements PayStrategy {
    @Override
    public void putMoney(VendingMachine vendingMachine) {
        vendingMachine.setBalance(vendingMachine.getBalance() + askMoney());
    }

    private int askMoney() {
        Scanner sc = new Scanner(System.in);
        MoneyTemplate[] moneyTemplates = MoneyTemplate.values();
        while (true) {
            System.out.print("\nEnter money (");
            for (int i = 0; i < moneyTemplates.length; i++) {
                if (i == moneyTemplates.length - 1) {
                    System.out.printf("%d): ", moneyTemplates[i].getValue());
                    break;
                }
                System.out.printf("%d, ", moneyTemplates[i].getValue());
            }

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

        MoneyTemplate[] moneyTemplates = MoneyTemplate.values();
        int[] valuesOfMoney = new int[moneyTemplates.length];

        for (int i = 0; i < moneyTemplates.length; i++) {
            valuesOfMoney[i] = moneyTemplates[i].getValue();
            if (moneyTemplates[i].getValue() == money) {
                return money;
            }
        }
        throw new RuntimeException("You can put only this type of money " + Arrays.toString(valuesOfMoney) + "!");
    }
}
