package game.kijelzo;

import game.objects.Bullets;
import game.objects.Enemy;
import game.objects.MyTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * A játék grafikus megjelenítéséért és logikájáért felelős osztály.
 * Ez az osztály kezeli a játék fő ciklusát, a játékobjektumok inicializálását és a felhasználói bemeneteket.
 */
public class Kijelzo extends JComponent {

    private Graphics2D g2d;
    private BufferedImage image;
    private int height;
    private int width;
    private Thread thread;
    private boolean start = true;
    private Key key;
    private int ShotsTime;

    private ArrayList<Bullets> bullets;
    private ArrayList<Enemy> enemies;

    private int score=0;
    private TopScoreManager topScoreManager;
    private TopScore topScore;

    //FPS
    private final int fps = 60;
    private final int target_time = 1000000000/fps;

    public MyTank mytank;

    /**
     * Elindítja a játékot.
     * Inicializálja az összes szükséges objektumot, és elindítja a fő játékciklust egy új szálban.
     */
    public void start(){
        width=getWidth();
        height=getHeight();
        topScoreManager = new TopScoreManager("topscore.txt");
        topScore = topScoreManager.loadTopScore();
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB); //Minden pixel egy int típusú számként tárolódik.
        //A = atlatszosag, RGB = szinek
        g2d = image.createGraphics();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(start){
                    long kezdesi_ido = System.nanoTime();
                    drawBackground();
                    drawGame();
                    render();
                    long time = System.nanoTime() - kezdesi_ido;
                    if(time < target_time){
                        long sleep=(target_time-time)/1000000;
                        sleep(sleep);
                    }
                    /*sleep(target_time);*/
                }
            }
        }
        );
        initObjects();
        initKeys();
        initBullets();
        thread.start();
    }

    /**
     * Inicializálja a játékobjektumokat, például a tankot és az ellenségeket.
     * Az ellenségek periodikusan generálódnak külön szálban.
     */
    private void initObjects(){
        mytank = new MyTank();
        mytank.changeLoc(200,200);
        enemies = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(start){
                    newEnemy();
                    sleep(3000);
                }
            }
        }).start();
    }

    /**
     * Inicializálja a játékban használt lövedékek listáját és azok frissítési ciklusát.
     * Gondoskodik arról, hogy a lövedékek animációja és törlése folyamatosan megtörténjen.
     */
    private void initBullets(){
        bullets = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(start){
                    for(int i=0; i<bullets.size();i++)
                    {
                        Bullets bullet = bullets.get(i);
                        if(bullet!=null){
                            bullet.update();
                            checkBullets(bullet);
                            if(!bullet.check(width,height)){
                                bullets.remove(bullet);
                            }
                        }
                        else{
                            bullets.remove(bullet);
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }

    /**
     * Beállítja a felhasználói bemenet kezelését.
     * Figyeli a billentyűzet lenyomását és felengedését, és ennek megfelelően állítja be a játék logikáját.
     */
    private void initKeys(){
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_A){
                    key.setTurnLeftkey(true);
                }
                else if(e.getKeyCode()==KeyEvent.VK_D){
                    key.setTurnRightkey(true);
                }
                else if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    key.setKey_space(true);
                }
                else if(e.getKeyCode()==KeyEvent.VK_J){
                    key.setKey_j(true);
                }
                else if(e.getKeyCode()==KeyEvent.VK_K){
                    key.setKey_k(true);
                }
                if(e.getKeyCode()==KeyEvent.VK_W){
                    key.setGoForWard(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_A){
                    key.setTurnLeftkey(false);
                }
                else if(e.getKeyCode()==KeyEvent.VK_D){
                    key.setTurnRightkey(false);
                }
                else if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    key.setKey_space(false);
                }
                else if(e.getKeyCode()==KeyEvent.VK_J){
                    key.setKey_j(false);
                }
                else if(e.getKeyCode()==KeyEvent.VK_K){
                    key.setKey_k(false);
                }
                else if(e.getKeyCode()==KeyEvent.VK_W){
                    key.setGoForWard(false);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                float c = 0.5f;
                while(start){
                    if(mytank.isAlive()){
                        float angle = mytank.getAngle();
                        if(key.isTurnLeftkey()) {
                            angle -= c;
                        }

                        if(key.isTurnRightkey()) {
                            angle += c;
                        }
                        if(key.isKey_j() || key.isKey_k()) {
                            ShotsTime++;
                            if (ShotsTime >= 30) {
                                if (key.isKey_j()) {
                                    bullets.add(new Bullets(mytank.getX(), mytank.getY(), mytank.getAngle(), 7, 3f));
                                } else {
                                    bullets.add(new Bullets(mytank.getX(), mytank.getY(), mytank.getAngle(), 20, 3f));
                                }
                                ShotsTime = 0;
                            }

                        }
                        if (key.isKey_space()) {
                            mytank.boost();
                        } else if (key.isgoForWard()) {
                            // Mozgás folyamatos előre, de boost nélkül
                            if (mytank.getSpeed() < 0.5f) {
                                mytank.setSpeed(0.5f); // Minimális sebesség
                            }
                        } else {
                            mytank.BackToNormalSPeed(); // Lassítás, ha nincs gomb nyomva
                        }
                        mytank.move();
                        mytank.changeAngle(angle);

                    }else {

                    }
                    if (!mytank.check(width, height)) {
                        System.out.println("Game Over! The tank left the screen.");
                        stopGame(); // Megállítja a játékot
                        break; // Kilép a szálból
                    }

                    for(int i=0;i<enemies.size();i++){
                        Enemy enemy = enemies.get(i);
                        if(enemy!=null){
                            enemy.move();
                            if(!enemy.check(width,height)){
                                enemies.remove(enemy);
                                System.out.println("1 enemy wasn't killed! Get better.");
                            }else {
                                if(mytank.isAlive()){
                                    checkMyTank(enemy);
                                }
                            }
                        }
                    }
                    sleep(5);
                }
            }
        }).start();
    }

    /**
     * Kirajzolja a játék hátterét.
     */
    private void drawBackground(){
        g2d.setColor(new Color(49, 154, 6));
        g2d.fillRect(0,0,width,height);
    }

    /**
     * Kirajzolja a játék állapotát.
     * Tartalmazza a tankot, ellenségeket, lövedékeket és egyéb információkat.
     */
    private void drawGame(){
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(mytank.isAlive()){
            mytank.draw(g2d);
        }else{
            if (score > topScore.getScore()) {
                topScore.setScore(score);
                topScoreManager.saveTopScore(topScore);
            }
            // Ha a tank halott, kiírjuk a "Game Over" üzenetet
            g2d.setFont(new Font("Arial", Font.BOLD, 48));
            g2d.setColor(Color.RED);
            String gameOverText = "GAME OVER";
            int textWidth = g2d.getFontMetrics().stringWidth(gameOverText);
            g2d.drawString(gameOverText, (width - textWidth) / 2, height / 2 - 20);

            g2d.setFont(new Font("Arial", Font.PLAIN, 24));
            g2d.setColor(Color.WHITE);
            String subText = "Better luck next time...";
            int subTextWidth = g2d.getFontMetrics().stringWidth(subText);
            g2d.drawString(subText, (width - subTextWidth) / 2, height / 2 + 20);
        }
        g2d.setColor(new Color(0,0,0));
        g2d.setFont(getFont().deriveFont(Font.ITALIC,16f));
        g2d.drawString("Score: " + score, 10, 20);

        g2d.setColor(new Color(0,0,0));
        g2d.setFont(getFont().deriveFont(Font.ITALIC,16f));
        g2d.drawString("Top Score: " + topScore.getScore(), width - g2d.getFontMetrics().stringWidth("Top Score: " + topScore.getScore()) - 10, 20);
        for(int i=0;i<bullets.size();i++){
            Bullets bullet = bullets.get(i);
            if(bullet!=null){
                bullet.draw(g2d);
            }
        }
        for(int i=0;i<enemies.size();i++){
            Enemy enemy = enemies.get(i);
            if(enemy!=null){
                enemy.draw(g2d);
            }
        }
    }

    /**
     * Megjeleníti a kirajzolt képet a képernyőn.
     */
    private void render(){
        Graphics gr = getGraphics();
        gr.drawImage(image,0,0,null);
        gr.dispose();
    }

    /**
     * Kényszerített várakozást végez a megadott ideig.
     *
     * @param speed Várakozási idő milliszekundumban.
     */
    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    /**
     * Létrehoz egy új ellenséget a képernyő szélén.
     */
    public void newEnemy(){
        Random ran = new Random();
        int locationY= ran.nextInt(height-50);
        Enemy enemy = new Enemy();
        enemy.changeLoc(0,locationY);
        enemy.changeAngle(0);
        enemies.add(enemy);
        int locY = ran.nextInt(height-50);
        Enemy secondEnemy = new Enemy();
        secondEnemy.changeLoc(width,locY);
        secondEnemy.changeAngle(180);
        enemies.add(secondEnemy);
    }


    /**
     * Megállítja a játékot.
     */
    private void stopGame() {
        start = false; // Megállítja a fő játékciklust
        thread.interrupt(); // Megszakítja a rajzolási szálat
    }

    /**
     * Ellenőrzi, hogy a lövedékek eltalálták-e az ellenségeket.
     *
     * @param bullet A vizsgált lövedék.
     */
    public void checkBullets(Bullets bullet){
        for(int i=0;i<enemies.size();i++){
            Enemy enemy = enemies.get(i);
            if(enemy!=null){
                Area area = new Area(bullet.getShape());
                area.intersect(enemy.getEnemyShape());
                if(!area.isEmpty()){
                    if(!enemy.updateHP(bullet.getSize())){
                        enemies.remove(enemy);
                        score++;
                    }

                    bullets.remove(bullet);


                }
            }
        }

    }

    /**
     * Ellenőrzi, hogy az ellenség ütközött-e a játékos tankjával.
     *
     * @param enemy Az ellenfél, amelyet ellenőrizni kell.
     */
    public void checkMyTank(Enemy enemy){
            if(enemy!=null){
                Area area = new Area(mytank.getMyTanksShape());
                area.intersect(enemy.getEnemyShape());
                if(!area.isEmpty()) {
                    System.out.println("talalkozott");
                    if(!enemy.updateHP(mytank.getHP())) {
                        enemies.remove(enemy);
                        System.out.println("torolte");
                    }
                    if(!mytank.updateHP(10)) {
                        mytank.setAlive(false);
                        System.out.println("setAlive false lett");
                    }
                }
            }
    }
}
