package store;

import java.math.BigDecimal;

public class Item {
    final Unit bottle;
    final String milk;
    final BigDecimal price;

    public Item(Unit bottle, String milk, double price) {
        this.bottle = bottle;
        this.milk = milk;
        this.price = BigDecimal.valueOf(price);
    }
}
