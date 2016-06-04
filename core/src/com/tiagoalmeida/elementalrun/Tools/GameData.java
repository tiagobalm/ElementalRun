package com.tiagoalmeida.elementalrun.Tools;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1;

    private final int MAX_SCORES = 3;
    private int[] highScores;

    private int testHighScore;

    public GameData() {
        highScores = new int[MAX_SCORES];
    }

    //Empty scores table
    public void init() {
        for(int i = 0; i < MAX_SCORES; i++)
            highScores[i] = 0;
    }

    public int[] getHighScores() { return highScores; }

    public int getTestHighScore() { return testHighScore; }
    public void setTestHighScore(int score) { this.testHighScore = score;}

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
