package org.henrysgrocery.store.ui;

import java.io.PrintStream;

class InvalidCommand implements Command {
    private static final String INVALID_ITEM = "--Invalid Item";

    private PrintStream out;

    InvalidCommand(PrintStream out) {
        this.out = out;
    }

    @Override
    public void execute(String command) {
        out.println(INVALID_ITEM);
    }
}
