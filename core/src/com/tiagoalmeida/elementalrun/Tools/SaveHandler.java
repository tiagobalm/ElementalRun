package com.tiagoalmeida.elementalrun.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Class to write and read serializable objects.
 */
public class SaveHandler {

    /**
     * Game data object that store all the information about levels and high scores.
     */
    public static GameData gameData;

    /**
     * Serializes serializable objects.
     * @param obj Class object to be serializable. Needs to be serializable.
     * @return byte array of serialized data.
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException{
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteArrayOut);
        objectOut.writeObject(obj);
        return byteArrayOut.toByteArray();
    }

    /**
     * Deserializes serialized objects.
     * @param objectBytes Byte array containing the information of the class object.
     * @return Constructor object from the serialized data.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] objectBytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(objectBytes);
        ObjectInputStream objectIn = new ObjectInputStream(byteArrayIn);
        return objectIn.readObject();
    }

    /**
     * Saves the current high scores and levels to a file.
     */
    public static void save(){
        try {
            OutputStream outputStream = null;
            FileHandle file = Gdx.files.local("highScores.save");
            file.writeBytes(serialize(gameData), false);
            if(outputStream != null) outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    /**
     * Loads current high scores and level from a file.
     */
    public static void load() {
        try {
            if(!saveFileExists()) {
                init();
                return;
            }
            InputStream inputStream = null;
            FileHandle fileHandle = Gdx.files.local("highScores.save");
            gameData = (GameData)deserialize(fileHandle.readBytes());
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    /**
     *
     * @return True if the save file exists and false otherwise.
     */
    public static boolean saveFileExists() {
        FileHandle saveFile = Gdx.files.local("highScores.save");
        return saveFile.exists();
    }

    /**
     * Initializes a new save file.
     */
    public static void init() {
        gameData = new GameData();
        gameData.init();
        save();
    }
}
