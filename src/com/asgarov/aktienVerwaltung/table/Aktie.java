package com.asgarov.aktienVerwaltung.table;

import com.asgarov.aktienVerwaltung.util.Reader;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Aktie implements Serializable {

    private static final String COMMA_DELIMITER = ",";

    private static final int DATE_INDEX = 0;
    private static final int OPEN_INDEX = 1;
    private static final int HIGH_INDEX = 2;
    private static final int LOW_INDEX = 3;
    private static final int CLOSE_INDEX = 4;
    private static final int ADJ_CLOSE_INDEX = 5;
    private static final int VOLUME_INDEX = 6;

    private String name;
    private String WKN;
    private String kuerzel;
    private List<KursDatei> kursDaten;

    public Aktie(String name, String WKN, String kuerzel) {
        this.name = name;
        this.WKN = WKN;
        this.kuerzel = kuerzel;
        kursDaten = new ArrayList<>();
    }

    /**
     * This method reads the 'KursDaten' from a file as a List of String lines
     * following which they are reverse sorted to bring latest ones up,
     * and added each into the list of KursDatei
     * @throws IOException
     */
    public void addKursDaten() throws IOException {
        List<String> records = Reader.readCSV(getKuerzel());

        records.stream()
                .sorted(Comparator.reverseOrder())
                .skip(1) //skipping the heading of the file: Date,Open,High,Low,Close,Adj Close,Volume
                .map(this::transformLineToKursDatei)
                .limit(30)
                .forEach(k -> kursDaten.add(k));
    }

    /**
     * Method transforms each String line into a KursDatei Object
     * @param line
     * @return
     */
    private KursDatei transformLineToKursDatei(String line) {
        String[] values = line.split(COMMA_DELIMITER);
        KursDatei kursDatei = new KursDatei();

        kursDatei.setDate(values[DATE_INDEX]);
        kursDatei.setOpen(values[OPEN_INDEX]);
        kursDatei.setHigh(values[HIGH_INDEX]);
        kursDatei.setLow(values[LOW_INDEX]);
        kursDatei.setClose(values[CLOSE_INDEX]);
        kursDatei.setAdjClose(values[ADJ_CLOSE_INDEX]);
        kursDatei.setVolume(values[VOLUME_INDEX]);

        return kursDatei;
    }

    /**
     * For each KursDatei in the list
     * Date is outputted following by the amount of starts corresponding to the close value of the object
     */
    public void plot() {
        if (kursDaten.isEmpty()) {
            System.out.println("Kurs Daten must be imported before they can be plotted");
            return;
        }

        kursDaten.forEach(k -> {
            System.out.print(k.getDate() + ": ");
            //parsing close value of the Aktie from String
            int close = Integer.parseInt(k.getClose().substring(0, k.getClose().indexOf(".")));

            for (int i = 0; i < close/4; i++) {  //dividing the length of output for 4 so that it fits better on the screen
                System.out.print("*");
            }
            System.out.println();
        });
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWKN() {
        return WKN;
    }

    public void setWKN(String WKN) {
        this.WKN = WKN;
    }

    public String getKuerzel() {
        return kuerzel;
    }

    public void setKuerzel(String kuerzel) {
        this.kuerzel = kuerzel;
    }

    public List<KursDatei> getKursDaten() {
        return kursDaten;
    }

    public void setKursDaten(List<KursDatei> kursDaten) {
        this.kursDaten = kursDaten;
    }


    /**
     * Equals method, that checks whether the Aktie is the same object
     * based on the value of name field (because that is the field being used to enter objects into hashtable)
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aktie aktie = (Aktie) o;

        return Objects.equals(name, aktie.name);
    }

    /**
     * Hashcode method, which is stored in HashTable class because I needed to use same method for storing and searching
     * @return
     */
    @Override
    public int hashCode() {
        return HashTable.hashCode(name);
    }


    /**
     * To String method that allows better display of the object in the console
     * @return
     */
    @Override
    public String toString() {
        return "Aktie{" +
                "name='" + name + '\'' +
                ", WKN='" + WKN + '\'' +
                ", kuerzel='" + kuerzel + '\'' +
                '}';
    }
}
