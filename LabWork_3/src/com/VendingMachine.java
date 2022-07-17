package com;

import com.PayStrategy.PayByCreditCard;
import com.PayStrategy.PayByRealMoney;
import com.PayStrategy.PayStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class VendingMachine {
    private int balance;
    private int total;
    private PayStrategy payingSystem;
    private Map<Integer, Product> products = new LinkedHashMap<>();

    public VendingMachine(int balance, Map<Integer, Product> products) {
        this.balance = balance;
        products.entrySet().stream()
                .sorted(Collections.reverseOrder(Comparator.comparingInt(e -> e.getValue().getPrice())))
                .forEach(e -> this.products.put(e.getKey(), e.getValue()));
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }

    public void putMoney() {
        switch (askPayingSystem()) {
            case 1:
                payingSystem = new PayByCreditCard();
                break;
            case 2:
                payingSystem = new PayByRealMoney();
                break;
        }
        payingSystem.putMoney(this);
    }

    private int askPayingSystem() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    1 -> Pay by credit card
                    2 -> Pay real money
                    """);
            System.out.print("Choose the way to pay: ");
            String choice = sc.nextLine().replaceAll("\\s+", "");
            try {
                return checkPayingSystem(choice);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int checkPayingSystem(String str) {
        if (str.equals(""))
            throw new RuntimeException("\nChoice can't be empty!\n");
        int choice = Integer.parseInt(str);
        if (choice < 1 || choice > 2)
            throw new RuntimeException("\nCan't find this way!\n");
        return choice;
    }

    public void printProducts() {
        System.out.println("\n<=== AVAILABLE PRODUCTS IN MACHINE ===>");
        System.out.println("""
                +-------+--------------------+
                | PRICE |        NAME        |
                +-------+--------------------+""");
        products.forEach((k, v) -> {
            System.out.printf("|  %-4d | %-18s |%n", v.getPrice(), v.getName());
            System.out.println("+-------+--------------------+");
        });
    }

    public void printBalance() {
        System.out.println("""
                
                +-----------+
                |  BALANCE  |
                +-----------+""");
        System.out.printf("|  %-8d |%n" +
                "+-----------+%n", balance);
    }

    public void printTotal() {
        System.out.println("""
                +-------------------------+
                | MACHINE EARNED IN TOTAL |
                +-------------------------+""");
        System.out.printf("          %d SOM%n" +
                "+-------------------------+%n", total);
    }

    public void buyProduct(Product product) {
        balance -= product.getPrice();
        total += product.getPrice();
        System.out.printf("%nYou bought %s for %d som!%n", product.getName(), product.getPrice());
    }
}