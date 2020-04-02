package org.henrysgrocery.store;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CommandLineInterface {
    private InputStream in;
    private PrintStream out;

    public void run(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;

        Scanner scanner = new Scanner(in);
        displayHeading(out);
        if (scanner.hasNext())
            displayExitMessage();
    }

    private void displayHeading(PrintStream out) {
        out.println("Henrys Store");
    }

    private void displayExitMessage() {
        out.println("Exit");
    }

}
