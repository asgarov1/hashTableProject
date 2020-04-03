package com.asgarov.aktienVerwaltung.util;

import com.asgarov.aktienVerwaltung.HashTable;

import java.io.*;

import static com.asgarov.aktienVerwaltung.util.Reader.PATH_TO_RESOURCES;

public class Serializer {
    private static final String SAVED_FILE_NAME = "hashtable.txt";
    private static final String SAVE_CONFIRMATION_MESSAGE = "Saved to " + SAVED_FILE_NAME;
    private static final String TABLE_NOT_FOUND_MESSAGE = "No saved hashtable found under " + PATH_TO_RESOURCES + SAVED_FILE_NAME;

    public static void saveHashtableToFile(HashTable hashTable) throws IOException {
        FileOutputStream fileOutputStream
                = new FileOutputStream(PATH_TO_RESOURCES + SAVED_FILE_NAME);
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(hashTable);
        objectOutputStream.flush();
        objectOutputStream.close();
        System.out.println(SAVE_CONFIRMATION_MESSAGE);
    }

    public static HashTable loadHashTableFromFile() throws IOException, ClassNotFoundException {
        if (!new File(PATH_TO_RESOURCES + SAVED_FILE_NAME).exists()) {
            System.out.println(TABLE_NOT_FOUND_MESSAGE);
            return null;
        }

        FileInputStream fileInputStream
                = new FileInputStream(PATH_TO_RESOURCES + SAVED_FILE_NAME);
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        HashTable hashTable = (HashTable) objectInputStream.readObject();
        objectInputStream.close();
        return hashTable;
    }
}
