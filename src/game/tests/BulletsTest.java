package game.tests;

import game.objects.Bullets;
import game.objects.MyTank;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.*;

public class BulletsTest {
    @Test
    void testUpdatePosition() {
        Bullets bullet = new Bullets(100, 100, 0, 10, 5f);

        bullet.update();

        assertEquals(150, bullet.getX(), 0.001, "Az X koordinátának növekednie kell az irány és sebesség alapján.");
        assertEquals(145, bullet.getY(), 0.001, "Az Y koordinátának változatlanul kell maradnia, mivel az irány 0 fok.");
    }

    @Test
    void testBoundaryCheck() {
        Bullets bullet = new Bullets(500, 500, 45, 10, 1f);

        boolean insideBounds = bullet.check(600, 600);
        boolean outsideBounds = bullet.check(400, 400);

        // Akkor
        assertTrue(insideBounds, "A lövedéknek a pálya határain belül kell lennie.");
        assertFalse(outsideBounds, "A lövedéknek a pálya határain kívül kell lennie.");
    }

    @Test
    void testGetShapeSimplified() {
        // Adott
        double initialX = 100;
        double initialY = 150;
        double bulletSize = 10;
        Bullets bullet = new Bullets(initialX, initialY, 0, bulletSize, 2f);

        double expectedX = initialX + MyTank.tankSize / 2 - bulletSize / 2;
        double expectedY = initialY + MyTank.tankSize / 2 - bulletSize / 2;
        Shape expectedShape = new Rectangle2D.Double(expectedX, expectedY, bulletSize, bulletSize);

        Shape actualShape = bullet.getShape();

        assertEquals(expectedShape.getBounds().getSize(), actualShape.getBounds().getSize(), "A lövedék alakzata tényleg megegyezik az elvárttal.");
    }

}
