package org.henrysgrocery.store.ui;

import org.henrysgrocery.store.Command;

import java.io.PrintStream;

class InvalidCommand implements Command {
    private static final String INVALID_ITEM = "--Invalid Item";

    private PrintStream out;

    public InvalidCommand(PrintStream out) {
        this.out = out;
    }

    @Override
    public void execute(String command) {
        out.println(INVALID_ITEM);
    }
}
