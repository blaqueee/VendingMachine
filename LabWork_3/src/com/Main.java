package com;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static VendingMachine vendingMachine;

    public static void main(String[] args) {
        initializeProducts();

        while (true) {
            vendingMachine.printProducts();
            vendingMachine.printBalance();
            Clickable action = createAction();
            if (action == null) {
                System.out.println("\n   <=== You quit! ===>\n");
                vendingMachine.printTotal();
                return;
            }
            action.click();
        }
    }

    private static Clickable createAction() {
        int action = askAction();
        switch (action) {
            case 0:
                return null;
            case 1:
                Clickable doAction = () -> vendingMachine.putMoney();
                return doAction;
            default:
                doAction = () -> vendingMachine.buyProduct(vendingMachine.getProducts().get(action));
                return doAction;
        }
    }

    private static int askAction() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            printActions();
            String number = sc.nextLine().replaceAll("\\s+", "");
            try {
                return checkAction(number);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int checkAction(String str) {
        var availableActions = getAvailableProducts();

        if (str.equals(""))
            throw new RuntimeException("\nChoice can't be empty!");
        int choice = Integer.parseInt(str);

        if (choice < 0)
            throw new RuntimeException("\nCan't find this action!");
        else if (choice == 0 || choice == 1)
            return choice;
        else if (!availableActions.containsKey(choice))
            throw new RuntimeException("\nCan't find this action!");
        return choice;
    }

    private static Map<Integer, Product> getAvailableProducts() {
        var products = vendingMachine.getProducts();
        return products.entrySet().stream()
                .filter(entrySet -> entrySet.getValue().getPrice() <= vendingMachine.getBalance())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static void printActions() {
        var availableActions = getAvailableProducts();

        System.out.println("\n1 -> Put money");
        if (!availableActions.isEmpty()) {
            availableActions.forEach((k, v) -> {
                System.out.printf("%d -> Buy '%s'%n", k, v.getName());
            });
        }
        System.out.println("0 - Quit");
        System.out.print("\nChoose action: ");
    }

    private static void initializeProducts() {
        List<Product> allProducts = new ArrayList<>(List.of(Product.values()));
        Map<Integer, Product> randomProducts = new HashMap<>();

        int key = 2;
        while (randomProducts.size() != 5) {
            Collections.shuffle(allProducts);
            Product product = allProducts.get(0);
            if (randomProducts.containsValue(product))
                continue;
            randomProducts.put(key, product);
            key++;
        }

        vendingMachine = new VendingMachine(0, randomProducts);
    }
}
