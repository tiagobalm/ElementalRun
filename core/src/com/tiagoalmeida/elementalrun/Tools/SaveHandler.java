package com.tiagoalmeida.elementalrun.Tools;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveHandler {

    public static GameData gameData;

    public static void save(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream("highScores.save")
            );
            outputStream.writeObject(gameData);
            outputStream.close();
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

            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream("highScores.save")
            );
            gameData = (GameData)inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists() {
        File saveFile = new File("highScores.save");
        return saveFile.exists();
    }

    public static void init() {
        gameData = new GameData();
        gameData.init();
        save();
    }
}
