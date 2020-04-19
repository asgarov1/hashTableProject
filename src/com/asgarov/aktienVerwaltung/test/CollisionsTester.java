package com.asgarov.aktienVerwaltung.test;

import com.asgarov.aktienVerwaltung.table.Aktie;
import com.asgarov.aktienVerwaltung.table.HashTable;

import java.util.Random;

/**
 * This class generates inputs and tests the number of collisions
 */
public class CollisionsTester {

    /**
     * The testing method
     * @param tableSize
     * @param numberOfAktien
     */
    public void testHashTable(int tableSize, int numberOfAktien) {
        HashTable hashTable = new HashTable(tableSize);
        for (int i = 1; i <= numberOfAktien; i++) {
            Aktie aktie = new Aktie(generateName());
            hashTable.placeData(aktie);
            System.out.println(i + ") Generated: " + aktie.getName() + " : " + HashTable.hashCode(aktie.getName()));
        }

        System.out.println("------------------");
        System.out.println("Total amount of hashCode collision handlings = " + hashTable.getCollisionsCount());
    }

    /**
     * Generates random name between 4 and 10 characters, first character is capital
     * @return
     */
    private String generateName() {
        //I pick between 4 and 10 as the sensible length for random Aktie name
        int nameLength = generateRandomNumber(4, 10);

        StringBuilder stringBuilder = new StringBuilder();
        int ASCII_A = 'A';
        int ASCII_Z = 'Z';
        stringBuilder.append((char)generateRandomNumber(ASCII_A, ASCII_Z)); //first letter is random capital letter

        int ASCII_a = 'a';
        int ASCII_z = 'z';
        for (int i = 1; i < nameLength; i++) {
            stringBuilder.append((char)generateRandomNumber(ASCII_a, ASCII_z));
        }
        return stringBuilder.toString();
    }

    /**
     * Generates random number within bounds
     * @param min
     * @param max
     * @return
     */
    private int generateRandomNumber(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    //Commented so as not to confuse with the other Runner classes main method -> which is where the main program is to be entered

//    public static void main(String[] args) {
//        new CollisionsTester().testHashTable(1000, 250);
//    }
}
