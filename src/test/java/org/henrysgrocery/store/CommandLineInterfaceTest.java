package org.henrysgrocery.store;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CommandLineInterfaceTest {
    @Test
    void displaysHeader() {
        CommandLineInterface commandLineInterface = new CommandLineInterface();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        InputStream inputStream = createInputStream("");

        commandLineInterface.run(inputStream, printStream);

        assertConsoleOutput(outputStream);
    }

    private ByteArrayInputStream createInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    private void assertConsoleOutput(ByteArrayOutputStream outputStream) {
        assertThat(outputStream.toString()).isEqualToIgnoringNewLines("Henrys Store");
    }
}
