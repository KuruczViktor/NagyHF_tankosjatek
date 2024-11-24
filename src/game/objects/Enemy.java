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
     * Constructor for Enemy.
     * Initializes the enemy's properties, including its health, image, and shape.
     */
    public Enemy() {
        super(new HealPoints(20, 20));

        this.image = new ImageIcon(getClass().getResource("/game/images/enemy.png")).getImage();
        Path2D p = new Path2D.Double();
        double halfSize = size / 2;
        double offsetX = -halfSize + halfSize;
        double offsetY = -halfSize + halfSize;

        // Define a square shape for the enemy.
        p.moveTo(offsetX, offsetY);                     // Top-left corner
        p.lineTo(offsetX + size, offsetY);              // Top-right corner
        p.lineTo(offsetX + size, offsetY + size);       // Bottom-right corner
        p.lineTo(offsetX, offsetY + size);              // Bottom-left corner
        enemyShape = new Area(p);
    }

    /**
     * Changes the angle of the enemy.
     * Ensures the angle remains within valid bounds (0-359 degrees).
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
     * Updates the enemy's location on the screen.
     *
     * @param x New X-coordinate.
     * @param y New Y-coordinate.
     */
    public void changeLoc(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Moves the enemy in the direction of its current angle.
     */
    public void move() {
        x = x + Math.cos(Math.toRadians(angle)) * speed;
        y = y + Math.sin(Math.toRadians(angle)) * speed;
    }

    /**
     * Retrieves the enemy's current X-coordinate.
     *
     * @return The X-coordinate of the enemy.
     */
    public double getX() {
        return x;
    }

    /**
     * Retrieves the enemy's current Y-coordinate.
     *
     * @return The Y-coordinate of the enemy.
     */
    public double getY() {
        return y;
    }

    /**
     * Retrieves the enemy's current angle of rotation.
     *
     * @return The angle of rotation in degrees.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Draws the enemy on the screen with the current transformation settings.
     *
     * @param g2d Graphics2D object used for rendering.
     */
    public void draw(Graphics2D g2d) {
        // Save the original transformation
        AffineTransform old = g2d.getTransform();

        // Set the enemy's position
        g2d.translate(x, y);

        // Rotate and draw the enemy's image
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 90), enemysize / 2, enemysize / 2);
        g2d.drawImage(image, tran, null);
        Shape shape = getEnemyShape();
        renderhp(g2d, shape, y);
        g2d.setTransform(old);
    }

    /**
     * Checks whether the enemy is within the bounds of the screen.
     *
     * @param width  Width of the screen.
     * @param height Height of the screen.
     * @return True if the enemy is within bounds, otherwise false.
     */
    public boolean check(int width, int height) {
        Rectangle size = getEnemyShape().getBounds();
        return !(x <= -size.getWidth() || y <= -size.getHeight() || x > width || y > height);
    }

    /**
     * Retrieves the transformed shape of the enemy based on its current position and angle.
     *
     * @return The transformed Area object representing the enemy's shape.
     */
    public Area getEnemyShape() {
        AffineTransform tf = new AffineTransform();
        tf.translate(x, y);
        tf.rotate(Math.toRadians(angle), size / 2, size / 2);
        return new Area(tf.createTransformedShape(enemyShape));
    }
}