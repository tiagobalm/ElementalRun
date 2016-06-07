package com.tiagoalmeida.elementalrun.Tools;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class that holds all the information about levels and high scores.
 */
public class GameData implements Serializable {

    private static final long serialVersionUID = 1;

    private final int MAX_SCORES = 3;
    private Integer[] highScores;
    private int currentLevel;

    /**
     * Game data constructor.
     */
    public GameData() {
        currentLevel = 1;
        highScores = new Integer[MAX_SCORES];
    }

    /**
     * Creates a new high scores array.
     */
    public void init() {
        for(int i = 0; i < MAX_SCORES; i++)
            highScores[i] = 0;
    }

    /**
     *
     * @return High scores array.
     */
    public Integer[] getHighScores() { return highScores; }

    /**
     *
     * @return Current level of the player, i.e, the max level possible to play.
     */
    public int getCurrentLevel() { return currentLevel; }

    /**
     * Sets current level to a specified value.
     * @param level to set current level.
     */
    public void setCurrentLevel(int level) { this.currentLevel = level;}

    /**
     *
     * @param score to be tested.
     * @return True if the score passed as argument made into the high scores list and is not there already
     * and false otherwise.
     */
    public boolean isHighScore(int score) {
        boolean isPresent = false;

        for(Integer i : highScores)
            if(score == i)
                isPresent = true;

        return !isPresent && score > highScores[MAX_SCORES - 1];
    }

    /**
     *  Replaces the last score with the score passes as argument.
     * @param score to be added to the high scores.
     */
    public void addHighScore(int score) {
        if(isHighScore(score)) {
            highScores[MAX_SCORES - 1] = score;
            sortHighScores();
        }
    }

    /**
     * Sorts the high scores array.
     */
    private void sortHighScores() {
        Arrays.sort(highScores);
        Collections.reverse(Arrays.asList(highScores));
    }
}
