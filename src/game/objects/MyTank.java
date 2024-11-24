package game.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

public class MyTank extends RenderHP {
    public static final double tankSize = 100;
    private double x;
    private double y;

    private boolean IsAlive;

    private final float boost_max = 1f;
    private float speed = 0f;
    private boolean isSpedUp;

    private float angle = 0f;
    private final Image image;
    private final Image image_spedup;
    private final Area MyTanksShape;

    /**
     * Konstruktor.
     * a tank képét, hp-ját és alakját.
     */
    public MyTank() {
        super(new HealPoints(50, 50));
        setAlive(true);
        this.image = new ImageIcon(getClass().getResource("/game/images/tank.png")).getImage();
        this.image_spedup = new ImageIcon(getClass().getResource("/game/images/tankspedup.png")).getImage();
        Path2D p = new Path2D.Double();

        // Define a square shape for the tank.
        p.moveTo(0, 0);
        p.lineTo(tankSize, 0);
        p.lineTo(tankSize, tankSize);
        p.lineTo(0, tankSize);
        MyTanksShape = new Area(p);
    }

    /**
     * Kirajzolja a kijelzőre a megfelelő transzformációkkal.
     *
     * @param g2d kirajzoláshoz
     */
    public void draw(Graphics2D g2d) {
        AffineTransform old = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        g2d.translate(x, y);
        transform.rotate(Math.toRadians(angle + 90), tankSize / 2, tankSize / 2);
        renderhp(g2d, getMyTanksShape(), y);
        g2d.drawImage(isSpedUp ? image_spedup : image, transform, null);
        g2d.setTransform(old);
    }

    /**
     * Megváltoztatja az irányát
     * Az irány ne legyen 0 és 360 fokon kívül.
     *
     * @param angle New angle in degrees.
     */
    public void changeAngle(float angle) {
        if (angle < 0) {
            angle = 359;
        }
        if (angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }

    /**
     * Beállítja a játékos helyzetét.
     *
     * @param x koordináta
     * @param y koordináta
     */
    public void changeLoc(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Növeli a tank sebességét, boostol.
     * Csak egy bizonyos max mennyiségig.
     */
    public void boost() {
        isSpedUp = true;
        if (speed > boost_max) {
            speed = boost_max;
        } else {
            speed += 0.01f;
        }
    }

    /**
     * A boostolás után visszaállítja az eredeti sebességre.
     * Nem lehet negatív a sebesség.
     */
    public void BackToNormalSPeed() {
        isSpedUp = false;
        if (speed < 0) {
            speed = 0;
        } else {
            speed -= 0.003f;
        }
    }

    /**
     * Megváltoztatja a helyzetét, mozgatja a játékost.
     */
    public void move() {
        x = x + Math.cos(Math.toRadians(angle)) * speed;
        y = y + Math.sin(Math.toRadians(angle)) * speed;
    }

    /**
     * Képernyőn belül marad-e az ellenség.
     *
     * @return Igaz, ha az ellenség még nem ment kívül a kijelzőn.
     */
    public boolean check(int width, int height) {
        return !(x <= -tankSize || y < -tankSize || x > width || y > height);
    }

    /**
     * Visszaadja az ellenség alakját.
     *
     * @return Az ellenség alakja.
     */
    public Area getMyTanksShape() {
        AffineTransform tf = new AffineTransform();
        tf.translate(x, y);
        tf.rotate(Math.toRadians(angle), tankSize / 2, tankSize / 2);
        return new Area(tf.createTransformedShape(MyTanksShape));
    }

    // Getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public void setSpeed(float speed) {
        this.speed = Math.min(speed, boost_max); // Maximum speed limitation
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isAlive() {
        return IsAlive;
    }

    public void setAlive(boolean alive) {
        IsAlive = alive;
    }
}