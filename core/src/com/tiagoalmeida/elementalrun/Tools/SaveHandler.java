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

public class SaveHandler {

    public static GameData gameData;

    public static byte[] serialize(Object obj) throws IOException{
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteArrayOut);
        objectOut.writeObject(obj);
        return byteArrayOut.toByteArray();

    }

    public static Object deserialize(byte[] objectBytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(objectBytes);
        ObjectInputStream objectIn = new ObjectInputStream(byteArrayIn);
        return objectIn.readObject();
    }

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

    public static void load() {
        try {
            if(!saveFileExists()) {
                init();
                return;
            }
            System.out.println("loading");
            InputStream inputStream = null;
            FileHandle fileHandle = Gdx.files.local("highScores.save");
            gameData = (GameData)deserialize(fileHandle.readBytes());
            if(inputStream != null) inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists() {
        FileHandle saveFile = Gdx.files.local("highScores.save");
        return saveFile.exists();
    }

    public static void init() {
        gameData = new GameData();
        gameData.init();
        save();
    }
}
