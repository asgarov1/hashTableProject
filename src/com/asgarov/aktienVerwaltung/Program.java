package com.asgarov.aktienVerwaltung;

import com.asgarov.aktienVerwaltung.util.Serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static final String NO_AKTIE_FOUND_MESSAGE = "No Aktie found under such name. Keep in mind that due to the nature of hash functions input is case sensitive";
    private static final String ADD = "ADD";
    private static final String DELETE = "DELETE";
    private static final String IMPORT = "IMPORT";
    private static final String SEARCH = "SEARCH";
    private static final String PLOT = "PLOT";
    private static final String SAVE = "SAVE";
    private static final String LOAD = "LOAD";
    private static final String EXIT = "QUIT";
    private static final String STARTING_MESSAGE = "Please enter a command (case insensitive). Available commands are:";
    private static final String SUCCESS = "SUCCESS";
    private static final String ANSWER_SIGN = "=>";

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
            drawBeforeMenuLine();
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
            executeImport();
        } else if (input.contains(SEARCH)) {
            executeSearch();
        } else if (input.contains(PLOT)) {
            executePlot();
        } else if (input.contains(SAVE)) {
            executeSave();
        } else if (input.contains(LOAD)) {
            executeLoad();
        }
        System.out.println();
    }

    private void executeLoad() {
        HashTable hashTable = null;
        try {
            hashTable = Serializer.loadHashTableFromFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (hashTable != null) {
            this.hashTable = hashTable;
        }
        System.out.println(SUCCESS + ": ");
        System.out.println("hashtable loaded.");
    }

    private void executeSave() {
        try {
            Serializer.saveHashtableToFile(hashTable);
        } catch (IOException e) {
            System.out.println("Problem saving hashTable.");
        }
    }

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

        System.out.print(ANSWER_SIGN + SUCCESS + ": ");
        System.out.println("KursDaten for the Aktie were successfully imported and saved in the Aktie. Here they are: ");
        aktie.getKursDaten().forEach(System.out::println);
    }

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

    private void executeAdd() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print(ANSWER_SIGN + ADD + ": ");
            System.out.println("Please enter the name, WKN and Kuerzel of the Aktie, seperated by SPACE.");
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

    private void drawBeforeMenuLine() {
        for (int i = 0; i < STARTING_MESSAGE.length(); i++) {
            System.out.print("_");
        }
        System.out.println();
    }
}
