package org.henrysgrocery.store;

public class Main {
    public static void main(String[] args) {
        runUserInterface();
    }

    private static void runUserInterface() {
        new CommandLineInterface().run(System.in, System.out);
    }
}
