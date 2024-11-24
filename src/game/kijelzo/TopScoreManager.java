package game.kijelzo;

import java.io.*;

public class TopScoreManager implements Serializable {
    private final String filePath;

    public TopScoreManager(String filePath) {
        this.filePath = filePath;
    }

    public TopScore loadTopScore() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (TopScore) ois.readObject();
        } catch (FileNotFoundException e) {
            // Ha a fájl nem létezik, új legjobb pontszámot adunk vissza
            return new TopScore(0);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Nem sikerült a betöltése a topscore-nak: " + e.getMessage());
            return new TopScore(0);
        }
    }

    public void saveTopScore(TopScore topScore) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(topScore);
        } catch (IOException e) {
            System.err.println("Nem sikerült elmenteni a topscore-t: " + e.getMessage());
        }
    }
}
