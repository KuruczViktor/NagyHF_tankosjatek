package game.tests;

import game.objects.Enemy;
import org.junit.jupiter.api.*;

import java.awt.geom.Area;

import static org.junit.jupiter.api.Assertions.*;

    class EnemyTest {

        private Enemy enemy;

        @BeforeEach
        void setUp() {
            enemy = new Enemy();
        }

        @Test
        void testChangeLocation() {
            enemy.changeLoc(100, 150);
            assertEquals(100, enemy.getX(), "X koordináta megfelelő.");
            assertEquals(150, enemy.getY(), "Y koordináta megfelelő.");
        }

        @Test
        void testMove() {
            enemy.changeLoc(0, 0);
            enemy.changeAngle(0); // Előre mozog
            enemy.move();
            assertTrue(enemy.getX() > 0, "Az X koordinátának nőnie kell.");
        }

        @Test
        void testGetEnemyShape() {
            enemy.changeLoc(50, 50);
            Area shape = enemy.getEnemyShape();
            assertNotNull(shape, "Az alakzat nem lehet null.");
        }

        @Test
        void testCheckEnemyOutOfLeftBounds() {
            Enemy enemy = new Enemy();
            enemy.changeLoc(-100, 50); //kívül esik
            int width = 100;
            int height = 100;

            assertFalse(enemy.check(width, height), "Az ellenségnek a bal oldalon kívül kell lennie.");
        }
    }

