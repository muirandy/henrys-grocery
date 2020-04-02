package org.henrysgrocery.store;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CommandLineInterfaceTest {

    private static final String HEADING = "Henrys Store";
    private static final String INVALID_ITEM = "Invalid Item";

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

        assertLastConsoleOutput("Exit");
    }

    @Test
    void ignoresInvalidCommands() {
        String invalid = "invalid";
        inputStream = createInputStream(invalid);

        commandLineInterface.run(inputStream, printStream);

        assertLastConsoleOutput(HEADING);
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

        assertLastConsoleOutput("Added 2 single apples");
    }

    private ByteArrayInputStream createInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    private void assertLastConsoleOutput(String expected) {
        String actual = outputStream.toString();
        String[] lines = actual.split(System.lineSeparator());
        String lastLine = lines[lines.length - 1];

        assertThat(lastLine).isEqualToIgnoringNewLines(expected);
    }
}
