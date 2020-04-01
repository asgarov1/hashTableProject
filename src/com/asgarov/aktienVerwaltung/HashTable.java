package com.asgarov.aktienVerwaltung;

public class HashTable {
    private Aktie[] table;
    public static final int SIZE = 1000;
    public static final int PRIME_NUMBER = 31;

    public HashTable(int tableSize) {
        table = new Aktie[tableSize];
    }

    public Aktie[] getTable() {
        return table;
    }

    public void placeData(Aktie aktie) {
        int index = 0;
        int finalHash = aktie.hashCode();
        while (table[finalHash] != null) {
            finalHash = (aktie.hashCode() + (++index * index)) % SIZE;
        }

        table[finalHash] = aktie;
    }

    public Aktie findAktie(String name) {
        int index = 0;
        int finalHash = hashCode(name);
        while (table[finalHash] != null && !table[finalHash].getName().equals(name)) {
            finalHash = (hashCode(name) + (++index * index)) % SIZE;
        }
        return table[finalHash];
    }

    public int findAktieIndex(String name) {
        int index = 0;
        int finalHash = hashCode(name);
        while (table[finalHash] != null && !table[finalHash].getName().equals(name)) {
            finalHash = (hashCode(name) + (++index * index)) % SIZE;
        }
        return finalHash;
    }

    public static int hashCode(String name){
        int hash = 1;
        char[] nameArray = name.toCharArray();
        for (char c : nameArray) {
            hash = PRIME_NUMBER * hash  + c;
        }
        return Math.abs(hash % HashTable.SIZE);
    }

    public boolean deleteAktie(String searchParameter) {
        if(findAktie(searchParameter) == null) {
            return false;
        }

        int index = findAktieIndex(searchParameter);
        table[index] = null;
        return true;
    }
}