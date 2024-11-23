package game.tests;

import game.objects.MyTank;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class MyTankTest {

    private MyTank tank;

    @BeforeEach
    void setUp() {
        tank = new MyTank();
    }

    @Test
    void testBoost() {
        tank.boost();
        assertTrue(tank.getSpeed() > 0, "A boost után a sebességnek növekednie kell.");
    }

    @Test
    void testCheckAlive() {
        assertTrue(tank.isAlive(), "Alapértelmezés szerint a tanknak élnie kell.");
        tank.setAlive(false);
        assertFalse(tank.isAlive(), "A tank állapota halott kell legyen.");
    }

    @Test
    void testMove() {
        tank.changeLoc(0, 0);
        tank.changeAngle(0);
        tank.boost();
        tank.move();
        assertTrue(tank.getX() > 0, "A tanknak előre kell mozognia.");
    }
}
