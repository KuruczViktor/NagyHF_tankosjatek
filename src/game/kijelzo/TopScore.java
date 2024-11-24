package game.kijelzo;

import java.io.*;

/**
 * Tárolja a legmagasabb pontszámot a játék története során.
 */
public class TopScore implements Serializable {
    private int score;

    public TopScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}