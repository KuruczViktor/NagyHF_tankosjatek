package game.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

public class Enemy extends RenderHP {

    public static final double enemysize = 50;
    private double x;
    private double y;
    private final float speed = 0.4f;
    private float angle = 0;
    private final Image image;
    private final int size = 50;
    // Stores the shape of the enemy
    private final Area enemyShape;

    /**
     * Konstruktor
     * Létrehozza az ellenség HP-ját, képét és alakját.
     */
    public Enemy() {
        super(new HealPoints(20, 20));

        this.image = new ImageIcon(getClass().getResource("/game/images/enemy.png")).getImage();

        Path2D p = new Path2D.Double();
        // Négyzetes alak az ellenséghez
        p.moveTo(0, 0);
        p.lineTo(size, 0);
        p.lineTo(size, size);
        p.lineTo(0, size);
        enemyShape = new Area(p);
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
        } else if (angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }

    /**
     * Megváltoztatja a helyzetét.
     *
     * @param x koordináta
     * @param y koordináta
     */
    public void changeLoc(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Mozgatja az ellenséget.
     */
    public void move() {
        x = x + Math.cos(Math.toRadians(angle)) * speed;
        y = y + Math.sin(Math.toRadians(angle)) * speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    /**
     * Kirajzolja a kijelzőre a megfelelő transzformációkkal.
     *
     * @param g2d kirajzoláshoz
     */
    public void draw(Graphics2D g2d) {
        // Eredeti
        AffineTransform old = g2d.getTransform();

        // Pozícióra helyezi
        g2d.translate(x, y);
        //Elforgatja és megrajzolja.
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 90), enemysize / 2, enemysize / 2);
        g2d.drawImage(image, tran, null);
        Shape shape = getEnemyShape();
        renderhp(g2d, shape, y);
        g2d.setTransform(old);
    }

    /**
     * Képernyőn belül marad-e az ellenség.
     *
     * @return Igaz, ha az ellenség még nem ment kívül a kijelzőn.
     */
    public boolean check(int width, int height) {
        Rectangle size = getEnemyShape().getBounds();
        return !(x <= -size.getWidth() || y <= -size.getHeight() || x > width || y > height);
    }

    /**
     * Visszaadja az ellenség alakját.
     *
     * @return Az ellenség alakja.
     */
    public Area getEnemyShape() {
        AffineTransform tf = new AffineTransform();
        tf.translate(x, y);
        tf.rotate(Math.toRadians(angle), size / 2, size / 2);
        return new Area(tf.createTransformedShape(enemyShape));
    }
}