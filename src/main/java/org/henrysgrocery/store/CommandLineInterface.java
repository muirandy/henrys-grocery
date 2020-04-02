package org.henrysgrocery.store;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CommandLineInterface {
    private static final String QUIT = "QUIT";

    private InputStream in;
    private PrintStream out;
    private Scanner scanner;

    public void run(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        scanner = new Scanner(in);

        runCli(out);
    }

    private void runCli(PrintStream out) {
        displayHeading(out);
        while (scanner.hasNext()) {
            String input = scanner.next();
            if (isQuitCommand(input)) {
                displayQuitMessage();
                break;
            }
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

}
