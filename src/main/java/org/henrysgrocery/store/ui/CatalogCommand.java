package org.henrysgrocery.store.ui;

import org.henrysgrocery.store.Item;
import org.henrysgrocery.store.ProductCatalog;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

class CatalogCommand implements Command {
    private PrintStream out;
    private ProductCatalog productCatalog = ProductCatalog.createProductCatalog();

    CatalogCommand(PrintStream out) {
        this.out = out;
    }

    @Override
    public void execute(String addCommand) {
        List<Item> itemsInInventory = productCatalog.inventory();
        String inventory = itemsInInventory.stream()
                                           .map(i -> String.format("Name: %s Unit: %s", i.name, i.unit.name().toLowerCase()))
                                           .collect(Collectors.joining(System.lineSeparator()));

        out.println(inventory);
    }
}
