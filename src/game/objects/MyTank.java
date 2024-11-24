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
     * Constructor for MyTank.
     * Initializes the tank's properties, including its health, images, and shape.
     */
    public MyTank() {
        super(new HealPoints(50, 50));
        setAlive(true);
        this.image = new ImageIcon(getClass().getResource("/game/images/tank.png")).getImage();
        this.image_spedup = new ImageIcon(getClass().getResource("/game/images/tankspedup.png")).getImage();
        Path2D p = new Path2D.Double();
        double halfSize = tankSize / 2;
        double offsetX = -halfSize + halfSize;
        double offsetY = -halfSize + halfSize;

        // Define a square shape for the tank.
        p.moveTo(offsetX, offsetY);                     // Top-left corner
        p.lineTo(offsetX + tankSize, offsetY);          // Top-right corner
        p.lineTo(offsetX + tankSize, offsetY + tankSize); // Bottom-right corner
        p.lineTo(offsetX, offsetY + tankSize);          // Bottom-left corner
        MyTanksShape = new Area(p);
    }

    /**
     * Draws the tank on the screen with the current transformation settings.
     *
     * @param g2d Graphics2D object used for rendering.
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
     * Changes the angle of the tank.
     * Ensures the angle remains within valid bounds (0-359 degrees).
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
     * Updates the tank's location on the screen.
     *
     * @param x New X-coordinate.
     * @param y New Y-coordinate.
     */
    public void changeLoc(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Activates the tank's boost mode, increasing its speed.
     * Caps the speed at the maximum boost value.
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
     * Gradually returns the tank's speed to its normal state after boosting.
     * Prevents the speed from going negative.
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
     * Moves the tank in the direction of its current angle.
     */
    public void move() {
        x = x + Math.cos(Math.toRadians(angle)) * speed;
        y = y + Math.sin(Math.toRadians(angle)) * speed;
    }

    /**
     * Checks whether the tank is within the bounds of the screen.
     *
     * @param width  Width of the screen.
     * @param height Height of the screen.
     * @return True if the tank is within bounds, otherwise false.
     */
    public boolean check(int width, int height) {
        return !(x <= -tankSize || y < -tankSize || x > width || y > height);
    }

    /**
     * Retrieves the transformed shape of the tank based on its current position and angle.
     *
     * @return The transformed Area object representing the tank's shape.
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