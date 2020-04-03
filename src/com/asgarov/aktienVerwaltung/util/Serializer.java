package com.asgarov.aktienVerwaltung.util;

import java.io.*;

import static com.asgarov.aktienVerwaltung.util.Reader.PATH_TO_RESOURCES;

public class Serializer {
    private static final String SAVE_CONFIRMATION_MESSAGE = "Saved to ";
    private static final String TABLE_NOT_FOUND_MESSAGE = "No saved hashtable found under " + PATH_TO_RESOURCES;

    /**
     * Saves object to file via serialization
     * @param object
     * @param fileName
     * @throws IOException
     */
    public static void saveObjectToFile(Object object, String fileName) throws IOException {
        FileOutputStream fileOutputStream
                = new FileOutputStream(PATH_TO_RESOURCES + fileName);
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
        System.out.println(SAVE_CONFIRMATION_MESSAGE + fileName);
    }

    /**
     * Loads object from file via serialization
     * @param fileName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException {
        if (!new File(PATH_TO_RESOURCES + fileName).exists()) {
            System.out.println(TABLE_NOT_FOUND_MESSAGE + fileName);
            return null;
        }

        FileInputStream fileInputStream
                = new FileInputStream(PATH_TO_RESOURCES + fileName);
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }
}
