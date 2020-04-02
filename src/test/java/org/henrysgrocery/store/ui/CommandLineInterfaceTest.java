package org.henrysgrocery.store.ui;

import org.henrysgrocery.store.Item;
import org.henrysgrocery.store.ProductCatalog;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CommandLineInterfaceTest {

    private static final String HEADING = "-- Henrys Store (type help for more information) --";
    private static final String USAGE =
            "Add an item to the basket:" + System.lineSeparator()
                    + "  add <quantity> <unit> <item>" + System.lineSeparator()
                    + "    eg: add 3 single apples" + System.lineSeparator()
                    + "Price up the basket:" + System.lineSeparator()
                    + "  price [+/- daysOffset]" + System.lineSeparator()
                    + "    eg: price" + System.lineSeparator()
                    + "    eg: price +5" + System.lineSeparator()
                    + "    eg: price -1" + System.lineSeparator()
                    + "Display this message:" + System.lineSeparator()
                    + "  help" + System.lineSeparator()
                    + "Quit:" + System.lineSeparator()
                    + "  quit";
    private static final String INVALID_ITEM = "--Invalid Item";
    private static final String EXIT_MESSAGE = "--Exit";
    private static final String TOTAL_BASKET_COST_MESSAGE = "--Total Basket Cost: ";
    private static final String ADDED_MESSAGE = "--Added ";

    private CommandLineInterface commandLineInterface = new CommandLineInterface();
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream printStream = new PrintStream(outputStream);
    private InputStream inputStream;


    @Test
    void displaysHeader() {
        inputStream = createInputStream("");

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(HEADING);
    }

    @Test
    void quits() {
        inputStream = createInputStream("quit");

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(EXIT_MESSAGE);
    }

    @Test
    void ignoresInvalidCommands() {
        String invalid = "invalid";
        inputStream = createInputStream(invalid);

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(INVALID_ITEM);
    }

    @Test
    void invalidItemCombination() {
        String invalid = "add 1 tin bread";
        inputStream = createInputStream(invalid);

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(INVALID_ITEM);
    }

    @Test
    void invalidUnit() {
        String invalid = "add 1 invalid bread";
        inputStream = createInputStream(invalid);

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(INVALID_ITEM);
    }

    @Test
    void addItem() {
        inputStream = createInputStream("add 2 single apples");

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(ADDED_MESSAGE + "2 single apples");
    }

    @Test
    void totalEmptyBasket() {
        inputStream = createInputStream("price");

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(TOTAL_BASKET_COST_MESSAGE + "0.00");
    }

    @Test
    void totalItems() {
        inputStream = createInputStream(
                "add 1 bottle milk",
                "add 1 single apples",
                "price");

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(TOTAL_BASKET_COST_MESSAGE + "1.40");
    }

    @Test
    void totalItemsAtStartOfOffer() {
        inputStream = createInputStream(
                "add 1 bottle milk",
                "add 1 single apples",
                "price +3");

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(TOTAL_BASKET_COST_MESSAGE + "1.39");
    }

    @Test
    void usage() {
        inputStream = createInputStream("help");

        commandLineInterface.run(inputStream, printStream);

        assertUsage();
    }

    @Test
    void catalog() {
        inputStream = createInputStream("catalog");

        commandLineInterface.run(inputStream, printStream);

        assertCatalog();
    }

    private ByteArrayInputStream createInputStream(String... lines) {
        String input = Arrays.stream(lines)
                             .collect(Collectors.joining(System.lineSeparator()));
        return new ByteArrayInputStream(input.getBytes());
    }

    private void assertLastConsoleOutput(String expected) {
        String actual = outputStream.toString();
        String[] lines = actual.split(System.lineSeparator());
        String lastLine = lines[lines.length - 1];

        assertThat(lastLine).isEqualToIgnoringNewLines(expected);
    }

    private void assertUsage() {
        String actual = outputStream.toString();
        assertThat(actual).isEqualToIgnoringNewLines(HEADING + USAGE);
    }

    private void assertCatalog() {
        String actual = outputStream.toString();
        List<Item> itemsInInventory = ProductCatalog.createProductCatalog().inventory();
        String inventory = itemsInInventory.stream()
                                           .map(i -> String.format("Name: %s Unit: %s", i.name, i.unit.name().toLowerCase()))
                                           .collect(Collectors.joining(System.lineSeparator()));
        assertThat(actual).isEqualToIgnoringNewLines(HEADING + inventory);

    }
}