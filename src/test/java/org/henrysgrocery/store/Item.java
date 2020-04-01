package org.henrysgrocery.store;

public class Item {
    final Unit bottle;
    final String milk;
    final double price;

    public Item(Unit bottle, String milk, double price) {
        this.bottle = bottle;
        this.milk = milk;
        this.price = price;
    }
}
