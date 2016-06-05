package com.tiagoalmeida.elementalrun.Tools;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1;

    private final int MAX_SCORES = 3;
    private Integer[] highScores;
    private int currentLevel;

    private int testHighScore;

    public GameData() {
        currentLevel = 1;
        highScores = new Integer[MAX_SCORES];
    }

    //Empty scores table
    public void init() {
        for(int i = 0; i < MAX_SCORES; i++)
            highScores[i] = 0;
    }

    public Integer[] getHighScores() { return highScores; }

    public int getCurrentLevel() { return currentLevel; }

    public void setCurrentLevel(int level) { this.currentLevel = level;}

    public boolean isHighScore(int score) { return score > highScores[MAX_SCORES - 1]; }

    public void addHighScore(int score) {
        if(isHighScore(score)) {
            highScores[MAX_SCORES - 1] = score;
            sortHighScores();
        }
    }

    private void sortHighScores() {
        Arrays.sort(highScores);
        Collections.reverse(Arrays.asList(highScores));
    }
}
