package org.henrysgrocery.store.ui;

import java.io.PrintStream;

class UsageCommand implements Command {
    private static final String USAGE_MESSAGE =
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
                    + "Show the Catalog of Items:"+ System.lineSeparator()
                    + "  catalog" + System.lineSeparator()
                    + "Quit:" + System.lineSeparator()
                    + "  quit";
    private PrintStream out;

    UsageCommand(PrintStream out) {
        this.out = out;
    }

    @Override
    public void execute(String command) {
        out.println(USAGE_MESSAGE);
    }
}
