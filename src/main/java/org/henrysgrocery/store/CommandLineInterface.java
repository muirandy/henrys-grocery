package org.henrysgrocery.store;

import java.io.InputStream;
import java.io.PrintStream;

public class CommandLineInterface {
    public void run(InputStream in, PrintStream out) {
        displayHeading(out);
    }

    private void displayHeading(PrintStream out) {
        out.println("Henrys Store");
    }
}
