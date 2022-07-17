package com;

public enum Product {
    CHIPS("Ships", 50),
    COLA("Cola", 70),
    SWEET("Sweet", 30),
    POPCORN("Popcorn", 50),
    PEPSI("Pepsi", 100),
    COFFEE("Coffee", 150),
    ICE_CREAM("Ice-cream", 25),
    BISCUIT("Biscuit", 45),
    BURGER("Burger", 130),
    TEA("Tea", 15);

    private String name;
    private int price;

    Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
