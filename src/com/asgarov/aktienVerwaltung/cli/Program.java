package com.asgarov.aktienVerwaltung.cli;

import com.asgarov.aktienVerwaltung.table.Aktie;
import com.asgarov.aktienVerwaltung.table.HashTable;
import com.asgarov.aktienVerwaltung.util.Serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* CLI interface and logic of the program menu */
public class Program {

    public static final String NO_AKTIE_FOUND_MESSAGE = "No Aktie found under such name. Keep in mind that due to the nature of hash functions input is case sensitive";
    private static final String ADD = "ADD";
    private static final String DELETE = "DELETE";
    private static final String IMPORT = "IMPORT";
    private static final String SEARCH = "SEARCH";
    private static final String PLOT = "PLOT";
    private static final String SAVE = "SAVE";
    private static final String LOAD = "LOAD";
    private static final String QUIT = "QUIT";
    private static final String STARTING_MESSAGE = "Please enter a command (case insensitive). Available commands are:";
    private static final String SUCCESS = "SUCCESS";
    private static final String ANSWER_SIGN = "=>";
    public static final String NO_FILE_NAME_ENTERED = "Error, no file name was entered";

    private List<String> commands;
    private Scanner scanner;
    private HashTable hashTable;


    public Program() {
        this.commands = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.hashTable = new HashTable(HashTable.SIZE);

        commands.add(ADD);
        commands.add(DELETE);
        commands.add(IMPORT);
        commands.add(SEARCH);
        commands.add(PLOT);
        commands.add(SAVE + " [file_name]");
        commands.add(LOAD + " [file_name]");
        commands.add(QUIT);
    }

    /**
     * This method starts the CLI and keeps running till 'quit' is entered
     */
    public void start() {
        String input = "";
        while (!input.toUpperCase().contains(QUIT)) {
            drawBeforeMenuLine();
            System.out.println(STARTING_MESSAGE);
            commands.forEach(s -> System.out.print(s + " | "));
            System.out.println();

            input = scanner.nextLine();
            System.out.println();
            executeCommand(input);
        }
    }

    /**
     * This method executed the corresponding method vase on the input (case insensitive)
     */
    private void executeCommand(String input) {
        String inputUpperCase = input.toUpperCase();

        if (inputUpperCase.contains(ADD)) {
            executeAdd();
        } else if (inputUpperCase.contains(DELETE)) {
            executeDelete();
        } else if (inputUpperCase.contains(IMPORT)) {
            executeImport();
        } else if (inputUpperCase.contains(SEARCH)) {
            executeSearch();
        } else if (inputUpperCase.contains(PLOT)) {
            executePlot();
        } else if (inputUpperCase.contains(SAVE)) {
            try {
                executeSave(getFileName(input));
            } catch (IndexOutOfBoundsException e) {
                System.out.println(ANSWER_SIGN + NO_FILE_NAME_ENTERED);
                return;
            }
        } else if (inputUpperCase.contains(LOAD)) {
            try {
                executeLoad(getFileName(input));
            } catch (IndexOutOfBoundsException e) {
                System.out.println(ANSWER_SIGN + NO_FILE_NAME_ENTERED);
                return;
            }
        } else if (!inputUpperCase.contains(QUIT)) {
            System.out.println(ANSWER_SIGN + "Command not recognized. Try again.");
        }
        System.out.println();
    }

    private String getFileName(String input) {
        return input.split(" ")[1];
    }

    /**
     * Executes load function: loads hashtable from previously saved file
     */
    private void executeLoad(String fileName) {
        if (fileName == null) {
            return;
        }

        HashTable hashTable = null;
        try {
            hashTable = (HashTable) Serializer.loadObjectFromFile(fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (hashTable != null) {
            this.hashTable = hashTable;
            System.out.print(ANSWER_SIGN + SUCCESS + ": ");
            System.out.println("hashtable loaded.");
        }
    }

    /**
     * Executes save function: saves hashtable to a file
     */
    private void executeSave(String fileName) {
        if (fileName == null || fileName.length() < 1) {
            System.out.println("Error, no file name was entered");
            return;
        }

        try {
            Serializer.saveObjectToFile(hashTable, fileName);
        } catch (IOException e) {
            System.out.println("Problem saving hashTable.");
        }
    }

    /**
     * Produces ASCII style plot into the console
     */
    private void executePlot() {
        System.out.print(ANSWER_SIGN + PLOT + ": ");
        System.out.print("Please enter the name of the (previously added) Aktie you would like to plot: ");
        String searchParameter = scanner.nextLine();

        Aktie aktie = hashTable.findAktie(searchParameter);
        if (aktie == null) {
            System.out.println(ANSWER_SIGN + NO_AKTIE_FOUND_MESSAGE);
            return;
        }

        aktie.plot();
    }

    /**
     * Executes import function: imports Kursdaten from a .csv file located in the resources folder
     * Important: .csv file's name should match Kuerzel of the Aktie
     */
    private void executeImport() {
        System.out.print(ANSWER_SIGN + IMPORT + ": ");
        System.out.print("Please enter the name of the (previously added) Aktie you would like to import the data to: ");
        String searchParameter = scanner.nextLine();

        Aktie aktie = hashTable.findAktie(searchParameter);
        if (aktie == null) {
            System.out.println(ANSWER_SIGN + NO_AKTIE_FOUND_MESSAGE);
            return;
        }

        try {
            aktie.addKursDaten();
        } catch (IOException e) {
            System.out.println(ANSWER_SIGN + "No file found under the Aktien's Kuerzel name.");
            System.out.println(ANSWER_SIGN + "Please add the necessary .csv file to src/resources");
            return;
        }

        aktie.getKursDaten().forEach(System.out::println);
        System.out.print(ANSWER_SIGN + SUCCESS + ": ");
        System.out.println("KursDaten for the Aktie were successfully imported and saved in the Aktie.");
    }

    /**
     * Deletes chosen Aktie from the hashtable
     */
    private void executeDelete() {
        System.out.print(ANSWER_SIGN + DELETE + ": ");
        System.out.print("Please enter the name of the Aktie you would like to delete: ");
        String searchParameter = scanner.nextLine();
        boolean deleted = hashTable.deleteAktie(searchParameter);
        if (deleted) {
            System.out.print(ANSWER_SIGN + SUCCESS + ": ");
            System.out.println("Aktie with the name " + searchParameter + " was successfully deleted.");
        } else {
            System.out.println(ANSWER_SIGN + NO_AKTIE_FOUND_MESSAGE);
        }
    }

    /**
     * Searches for the Aktie in the hashtable via inputted Name
     */
    private void executeSearch() {
        System.out.print(ANSWER_SIGN + SEARCH + ": ");
        System.out.print("Please enter the NAME of the Aktie you would like to search for: ");
        String searchParameter = scanner.nextLine();
        Aktie aktie = hashTable.findAktie(searchParameter);
        if (aktie == null) {
            System.out.println(ANSWER_SIGN + NO_AKTIE_FOUND_MESSAGE);
        } else {
            System.out.print(ANSWER_SIGN + SUCCESS + ": ");
            System.out.println("Found: " + aktie);
        }
    }

    /**
     * Adds Aktie into the hashtable
     */
    private void executeAdd() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print(ANSWER_SIGN + ADD + ": ");
            System.out.println("Please enter the Name, WKN and Kuerzel of the Aktie, seperated by SPACE.");
            String[] parameters = scanner.nextLine().split(" ");
            Aktie aktie;
            try {
                aktie = new Aktie(parameters[0], parameters[1], parameters[2]);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(ANSWER_SIGN + "You forgot one of the parameters. Please try again.");
                continue;
            }
            hashTable.placeData(aktie);
            System.out.print(ANSWER_SIGN + SUCCESS + ": ");
            System.out.println(aktie + " was added successfully. You can search for it by it's name.");
            keepGoing = false;
        }
    }

    /**
     * Formatting method: draws a line before initial (recurring) options message
     */
    private void drawBeforeMenuLine() {
        for (int i = 0; i < STARTING_MESSAGE.length(); i++) {
            System.out.print("_");
        }
        System.out.println();
    }
}
