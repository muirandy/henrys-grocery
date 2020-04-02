package org.henrysgrocery.store.ui;

import org.henrysgrocery.store.*;

import java.io.PrintStream;
import java.util.regex.Matcher;

class AddCommand implements Command {
    private static final String ADD_TO_BASKET_MESSAGE = "--Added %d %s %s";
    private static final String INVALID_ITEM_MESSAGE = "--Invalid Item";

    private PrintStream out;
    private Basket basket;

    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();

    public AddCommand(PrintStream out, Basket basket) {
        this.out = out;
        this.basket = basket;
    }

    @Override
    public void execute(String addCommand) {
        try {
            addItemToBasket(addCommand);
        } catch (ProductCatalog.InvalidProductException | IllegalArgumentException e) {
            out.println(INVALID_ITEM_MESSAGE);
        }
    }

    private void addItemToBasket(String addCommand) {
        Matcher matcher = prepareMatcher(addCommand);

        int quantity = readAddItemQuantity(matcher);
        String unit = readAddItemUnit(matcher);
        String itemName = readAddItemName(matcher);

        Item item = productCatalog.getItem(Unit.valueOf(unit), itemName);
        basket.add(quantity, item);
        acknowledgeItemAdded(quantity, item);
    }

    private Matcher prepareMatcher(String addCommand) {
        Matcher matcher = obtainAddMatcher(addCommand);
        matcher.find();
        return matcher;
    }

    private Matcher obtainAddMatcher(String addCommand) {
        return CommandFactory.ADD.matcher(addCommand);
    }

    private int readAddItemQuantity(Matcher matcher) {
        return Integer.parseInt(matcher.group(1));
    }

    private String readAddItemUnit(Matcher matcher) {
        return matcher.group(2).toUpperCase();
    }

    private String readAddItemName(Matcher matcher) {
        return matcher.group(3);
    }

    private void acknowledgeItemAdded(int quantity, Item item) {
        String message = String.format(ADD_TO_BASKET_MESSAGE, quantity, item.unit.name().toLowerCase(), item.name);
        out.println(message);
    }
}
