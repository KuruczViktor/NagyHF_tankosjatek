package game.kijelzo;

import game.objects.MyTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Kijelzo extends JComponent {



    private Graphics2D g2d;
    private BufferedImage image;
    private int height;
    private int width;
    private Thread thread;
    private boolean start = true;
    private Key key;

    //FPS
    private final int fps = 30;
    private final int target_time = 1000000000/fps;



    private MyTank mytank;

    public void start(){
        width=getWidth();
        height=getHeight();
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB); //INT: Minden pixel egy int (32 bit) típusú számként tárolódik.
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
        thread.start();
    }

    public void initObjects(){
        mytank = new MyTank();
        mytank.changeLoc(200,200);
    }

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
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                float c = 0.5f;
                while(start){
                    float angle = mytank.getAngle();
                    if(key.isTurnLeftkey()) {
                        angle -= c;
                    }

                    if(key.isTurnRightkey()) {
                        angle += c;
                    }
                    if (key.isKey_space()) {
                        mytank.boost();
                    } else if (key.isKey_k()) {
                        // Mozgás folyamatos előre, de boost nélkül
                        if (mytank.getSpeed() < 0.5f) {
                            mytank.setSpeed(0.5f); // Minimális sebesség
                        }
                    } else {
                        mytank.BackToNormalSPeed(); // Lassítás, ha nincs gomb nyomva
                    }
                    mytank.move();
                    mytank.changeAngle(angle);
                    sleep(5);
                }
            }
        }).start();
    }


    private void drawBackground(){
        g2d.setColor(new Color(49, 154, 6));
        g2d.fillRect(0,0,width,height);
    }

    private void drawGame(){
        mytank.draw(g2d);
    }

    private void render(){
        Graphics gr = getGraphics();
        gr.drawImage(image,0,0,null);
        gr.dispose();
    }

    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
}
