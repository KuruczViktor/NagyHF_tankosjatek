package game.kijelzo;

import java.io.*;

public class TopScore implements Serializable {
    private static final long serialVersionUID = 1L; // Szerializációs azonosító
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