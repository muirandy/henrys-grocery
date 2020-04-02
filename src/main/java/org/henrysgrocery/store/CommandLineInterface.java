package org.henrysgrocery.store;

import org.henrysgrocery.store.ui.CommandFactory;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CommandLineInterface {
    private static final String QUIT = "QUIT";

    private static final String HEADER = "--Henrys Store--";
    private static final String EXIT_MESSAGE = "--Exit";


    private PrintStream out;
    private Scanner scanner;
    private CommandFactory commandFactory;

    public void run(InputStream in, PrintStream out) {
        this.out = out;
        scanner = new Scanner(in);
        commandFactory = new CommandFactory(out);
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
        commandFactory.create(command).execute(command);
    }
}