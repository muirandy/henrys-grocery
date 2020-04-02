package org.henrysgrocery.store;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineInterface {
    private static final String QUIT = "QUIT";
    private static final Pattern ADD = Pattern.compile("add ([0-9]+) ([A-Za-z]+) ([A-Za-z]+)");

    private PrintStream out;
    private Scanner scanner;
    private Basket basket;
    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();

    public void run(InputStream in, PrintStream out) {
        this.out = out;
        scanner = new Scanner(in);
        basket = Basket.create();

        runCli();
    }

    private void runCli() {
        displayHeading(out);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if (isQuitCommand(input)) {
                displayQuitMessage();
                break;
            }
            processCommand(input);
        }
    }

    private void displayHeading(PrintStream out) {
        out.println("Henrys Store");
    }

    private boolean isQuitCommand(String input) {
        return input.equalsIgnoreCase(QUIT);
    }

    private void displayQuitMessage() {
        out.println("Exit");
    }

    private void processCommand(String command) {
        if (isAddCommand(command)) {
            processAddCommand(command);
        }
    }

    private boolean isAddCommand(String next) {
        Matcher matcher = obtainMatcher(next);
        return matcher.find();
    }

    private void processAddCommand(String addCommand) {
        try {
            addItemToBasket(addCommand);
        } catch (ProductCatalog.InvalidProductException | IllegalArgumentException e) {
            out.println("Invalid Item");
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

    private String readAddItemName(Matcher matcher) {
        return matcher.group(3);
    }

    private String readAddItemUnit(Matcher matcher) {
        return matcher.group(2).toUpperCase();
    }

    private int readAddItemQuantity(Matcher matcher) {
        return Integer.parseInt(matcher.group(1));
    }

    private Matcher prepareMatcher(String addCommand) {
        Matcher matcher = obtainMatcher(addCommand);
        matcher.find();
        return matcher;
    }

    private Matcher obtainMatcher(String addCommand) {
        return ADD.matcher(addCommand);
    }

    private void acknowledgeItemAdded(int quantity, Item item) {
        String message = String.format("Added %d %s %s", quantity, item.unit.name().toLowerCase(), item.name);
        out.println(message);
    }
}