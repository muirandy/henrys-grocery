package org.henrysgrocery.store;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineInterface {
    private static final String QUIT = "QUIT";
    private static final Pattern ADD = Pattern.compile("add ([0-9]+) ([A-Za-z]+) ([A-Za-z]+)");
    private static final Pattern PRICE_UP = Pattern.compile("price( [+-][0-9]+)?");

    private static final String HEADER = "--Henrys Store--";
    private static final String INVALID_ITEM = "--Invalid Item";
    private static final String ADD_TO_BASKET_MESSAGE = "--Added %d %s %s";
    private static final String PRICE_UP_MESSAGE = "--Total Basket Cost: ";
    private static final String EXIT_MESSAGE = "--Exit";

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
        out.println(HEADER);
    }

    private boolean isQuitCommand(String input) {
        return input.equalsIgnoreCase(QUIT);
    }

    private void displayQuitMessage() {
        out.println(EXIT_MESSAGE);
    }

    private void processCommand(String command) {
        if (isAddCommand(command))
            processAddCommand(command);
        else if (isPriceUpCommand(command))
            processPriceUpCommand(command);
        else
            processInvalidCommand();
    }

    private boolean isAddCommand(String command) {
        Matcher matcher = obtainAddMatcher(command);
        return matcher.find();
    }

    private void processAddCommand(String addCommand) {
        try {
            addItemToBasket(addCommand);
        } catch (ProductCatalog.InvalidProductException | IllegalArgumentException e) {
            out.println(INVALID_ITEM);
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
        Matcher matcher = obtainAddMatcher(addCommand);
        matcher.find();
        return matcher;
    }

    private Matcher obtainAddMatcher(String addCommand) {
        return ADD.matcher(addCommand);
    }

    private void acknowledgeItemAdded(int quantity, Item item) {
        String message = String.format(ADD_TO_BASKET_MESSAGE, quantity, item.unit.name().toLowerCase(), item.name);
        out.println(message);
    }

    private boolean isPriceUpCommand(String command) {
        Matcher matcher = obtainPriceUpMatcher(command);
        return matcher.find();
    }

    private Matcher obtainPriceUpMatcher(String addCommand) {
        return PRICE_UP.matcher(addCommand);
    }

    private void processPriceUpCommand(String command) {
        long purchaseDaysOffset = readPurchaseDaysOffset(command);
        LocalDate purchaseDate = LocalDate.now().plusDays(purchaseDaysOffset);
        BigDecimal total = basket.priceUp(BasketPricerCreator.forDay(purchaseDate));
        displayBasketTotal(total);
    }

    private long readPurchaseDaysOffset(String command) {
        Matcher matcher = obtainPriceUpMatcher(command);
        matcher.find();
        String daysOffset = Optional.ofNullable(matcher.group(1)).orElse("0");
        return Long.parseLong(daysOffset.trim());
    }

    private void displayBasketTotal(BigDecimal total) {
        total = total.setScale(2, RoundingMode.CEILING);
        out.println(PRICE_UP_MESSAGE + total);
    }

    private void processInvalidCommand() {
        out.println(INVALID_ITEM);
    }
}