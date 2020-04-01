package com.asgarov.aktienVerwaltung;

import java.util.Objects;

public class Aktie {

    public static final int PRIME_NUMBER = 31;
    private String name;
    private String WKN;
    private String kuerzel;

    public Aktie(String name, String WKN, String kuerzel) {
        this.name = name;
        this.WKN = WKN;
        this.kuerzel = kuerzel;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aktie aktie = (Aktie) o;

        return Objects.equals(name, aktie.name);
    }

    @Override
    public int hashCode() {
        return HashTable.hashCode(name);
    }

    @Override
    public String toString() {
        return "Aktie{" +
                "name='" + name + '\'' +
                ", WKN='" + WKN + '\'' +
                ", kuerzel='" + kuerzel + '\'' +
                '}';
    }
}
