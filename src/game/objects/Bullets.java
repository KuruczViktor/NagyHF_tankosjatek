package game.objects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

public class Bullets {
    private double x;
    private double y;
    private final Color cl = new Color(0,0,100);
    private final Shape shape;
    private float angle;
    private double size;
    private float speed = 1f;

    /**
     * Ellenőrzi, kilépett-e a játék határain kívülre.
     * @param width
     * @param height
     * @return false, ha kilépett
     */
    public boolean check(int width, int height){
        if(x <= -size ||y< -size || x > width ||y>height){
            return false;
        }
        else{
            return true;
        }
    }

    public Bullets(double x, double y, float angle, double size, float speed) {
        x+=MyTank.tankSize/2-(size/2);
        y+=MyTank.tankSize/2-(size/2);
        this.x = x;
        this.y=y;
        this.angle = angle;
        this.size = size;
        this.speed = speed;
        shape = new Rectangle2D.Double(0,0,size,size);
    }

    /**
     * Frissíti a lövedék pozícióját.
     */
    public void update(){
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
    }

    /**
     * Megrajzolja a lövedéket.
     * @param g2d
     */
    public void draw(Graphics2D g2d){
        AffineTransform oldTrfs = g2d.getTransform();
        g2d.setColor(cl);
        g2d.translate(x,y);
        g2d.fill(shape);
        g2d.setTransform(oldTrfs);
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getSize(){
        return size;
    }

    public Shape getShape(){
        return new Area(new Rectangle2D.Double(x,y,size,size));
    }

}
