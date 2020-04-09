package com.asgarov.aktienVerwaltung.table;

import java.io.Serializable;

public class HashTable implements Serializable {

    private Aktie[] table;
    public static final int SIZE = 1000;
    public static final int PRIME_NUMBER = 31;

    public HashTable(int tableSize) {
        table = new Aktie[tableSize];
    }

    /**
     * Places Aktie object into the hashtable array using hashing function to determine index
     * For collision handling a 'quadratische Sondierung' is used
     * @param aktie
     */
    public void placeData(Aktie aktie) {
        int index = 0;
        int finalHash = aktie.hashCode();

        int count = 0;
        while (table[finalHash] != null) {
            finalHash = (aktie.hashCode() + (++index * index)) % SIZE;
            count++;
        }

        //Hard to reproduce such scenario but it is theoretically possible
        if(count > SIZE) {
            throw new RuntimeException("ERROR: Collision handling can't find a place in the hashtable.");
        }

        table[finalHash] = aktie;
    }

    /**
     * Looks for Aktie object inside of the hashtable using the same hashing function
     * Once found it checks if the Name field really equals the searched one as it is possible
     * due to collision handling that it needs to keep looking, in which case it will use the
     * same collision handling function to keep searching
     * @param name
     * @return
     */
    public Aktie findAktie(String name) {
        int index = 0;
        int finalHash = hashCode(name);
        while (table[finalHash] != null && !table[finalHash].getName().equals(name)) {
            finalHash = (hashCode(name) + (++index * index)) % SIZE;
        }
        return table[finalHash];
    }

    /**
     * Searches for the index of the objects in the HashTable array
     * This is needed when deleting the object from the table
     * @param name
     * @return
     */
    public int findAktieIndex(String name) {
        int index = 0;
        int finalHash = hashCode(name);
        while (table[finalHash] != null && !table[finalHash].getName().equals(name)) {
            finalHash = (hashCode(name) + (++index * index)) % SIZE;
        }
        return finalHash;
    }

    /**
     * Hashcode function used in this program
     * @param name
     * @return
     */
    public static int hashCode(String name) {
        int hash = 1;
        char[] nameArray = name.toCharArray();
        for (char c : nameArray) {
            hash = PRIME_NUMBER * hash + c;
        }
        return Math.abs(hash % HashTable.SIZE);
    }

    /**
     * Deletes Aktie object from hashtable
     * @param searchParameter
     * @return true if object found and deleted, else false
     */
    public boolean deleteAktie(String searchParameter) {
        if (findAktie(searchParameter) == null) {
            return false;
        }

        int index = findAktieIndex(searchParameter);
        table[index] = null;
        return true;
    }

}
