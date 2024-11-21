package game.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class MyTank {
    public static final double tankSize = 100;
    private double x;
    private double y;

    private final float boost_max = 1f;
    private float speed = 0f;
    private boolean isSpedUp;

    private float angle=0f;
    private final Image image;
    private final Image image_spedup;
    public MyTank() {
        this.image = new ImageIcon(getClass().getResource("/game/images/tank.png")).getImage();
        this.image_spedup = new ImageIcon(getClass().getResource("/game/images/tankspedup.png")).getImage();
    }

    public void draw(Graphics2D g2d){
        AffineTransform old = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        g2d.translate(x,y);
        transform.rotate(Math.toRadians(angle+90), tankSize / 2, tankSize / 2);


        g2d.drawImage(isSpedUp ? image_spedup : image,transform,null);
        g2d.setTransform(old);
    }

    public void changeAngle(float angle){
        if(angle<0){
            angle = 359;
        }
        if(angle>359){
            angle = 0;
        }
        this.angle=angle;
    }

    public void changeLoc(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void boost(){
        isSpedUp = true;
        if(speed>boost_max){
            speed = boost_max;
        }else{
            speed += 0.01f;
        }

    }

    public void BackToNormalSPeed(){
        isSpedUp = false;
        if(speed < 0){
            speed = 0;
        }
        else{
            speed -= 0.003f;
        }
    }

    public void move(){
        x= x + Math.cos(Math.toRadians(angle))*speed;
        y= y + Math.sin(Math.toRadians(angle))*speed;
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

    public void setSpeed(float speed) {
        this.speed = Math.min(speed, boost_max); // Maximum sebesség korlátozása
    }

    public float getSpeed() {
        return speed;
    }
    public boolean check(int width, int height){
        if(x <= -tankSize ||y < -tankSize || x > width ||y>height){
            return false;
        }
        else{
            return true;
        }
    }
}
