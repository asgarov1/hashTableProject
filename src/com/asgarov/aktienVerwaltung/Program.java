package com.asgarov.aktienVerwaltung;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    private static final String ADD = "ADD";
    private static final String DELETE = "DELETE";
    private static final String IMPORT = "IMPORT";
    private static final String SEARCH = "SEARCH";
    private static final String PLOT = "PLOT";
    private static final String SAVE = "SAVE";
    private static final String LOAD = "LOAD";
    private static final String EXIT = "QUIT";
    private static final String STARTING_MESSAGE = "Please enter a command. Available commands are:";

    private List<String> commands = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private HashTable hashTable = new HashTable(HashTable.SIZE);


    public Program() {
        commands.add(ADD);
        commands.add(DELETE);
        commands.add(IMPORT);
        commands.add(SEARCH);
        commands.add(PLOT);
        commands.add(SAVE + " [file_name]");
        commands.add(LOAD + " [file_name]");
        commands.add(EXIT);
    }

    public void start() {
        String input = "";
        while (!input.toUpperCase().contains("QUIT")) {
            System.out.println(STARTING_MESSAGE);
            commands.forEach(s -> System.out.print(s + " | "));
            System.out.println();

            input = scanner.nextLine();
            System.out.println();
            executeCommand(input);
        }
    }

    private void executeCommand(String input) {
        input = input.toUpperCase();

        if (input.contains(ADD)) {
            executeAdd();
        } else if (input.contains(DELETE)) {
            executeDelete();
        } else if (input.contains(IMPORT)) {
            //TODO
        } else if (input.contains(SEARCH)) {
            executeSearch();
        } else if (input.contains(PLOT)) {
            //TODO
        } else if (input.contains(SAVE)) {
            //TODO
        } else if (input.contains(LOAD)) {
            //TODO
        }
        System.out.println();
    }

    private void executeDelete() {
        System.out.print(DELETE + ": ");
        System.out.print("Please enter the name of the Aktie you would like to delete: ");
        String searchParameter = scanner.nextLine();
        boolean deleted = hashTable.deleteAktie(searchParameter);
        if(deleted) {
            System.out.println("Aktie with the name " + searchParameter + " was successfully deleted.");
        } else {
            System.out.println("No Aktie was found matching the criteria.");
        }
    }

    private void executeSearch() {
        System.out.print(SEARCH + ": ");
        System.out.println("Please enter the name of the Aktie you would like to search for");
        String searchParameter = scanner.nextLine();
        Aktie aktie = hashTable.findAktie(searchParameter);
        if (aktie == null) {
            System.out.println("No Aktie found under such name.");
        } else {
            System.out.println("Found: " + aktie);
        }
    }

    private void executeAdd() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print(ADD + ": ");
            System.out.println("Please enter the name, WKN and Kuerzel of the Aktie, seperated by !space!");
            String[] parameters = scanner.nextLine().split(" ");
            Aktie aktie;
            try {
                aktie = new Aktie(parameters[0], parameters[1], parameters[2]);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You forgot one of the parameters. Please try again");
                continue;
            }
            hashTable.placeData(aktie);
            System.out.println(aktie + " was added successfully. You can search for it via it's name");
            keepGoing = false;
        }

    }
}
