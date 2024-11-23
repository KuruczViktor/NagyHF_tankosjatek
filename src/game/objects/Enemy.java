package game.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

public class Enemy extends RenderHP{

    public static final double enemysize = 50;
    private double x;
    private double y;
    private final float speed = 0.4f;
    private float angle = 0;
    private final Image image;
    private final int size = 50;
    //Alakot tárol az Area
    private final Area enemyShape;

    public Enemy(){
        super(new HealPoints(20,20));

        this.image = new ImageIcon(getClass().getResource("/game/images/enemy.png")).getImage();
        Path2D p = new Path2D.Double();
        double halfSize = size/2;
        double offsetX = -halfSize + halfSize;
        double offsetY = -halfSize + halfSize;

// Négyzet definiálása
        p.moveTo(offsetX, offsetY);                    // Bal felső sarok
        p.lineTo(offsetX + size, offsetY);             // Jobb felső sarok
        p.lineTo(offsetX + size, offsetY + size);      // Jobb alsó sarok
        p.lineTo(offsetX, offsetY + size);
        enemyShape = new Area(p);
    }






    public void changeAngle(float angle){
        if(angle<0){
            angle = 359;
        }
        else if(angle>359){
            angle = 0;
        }
        this.angle=angle;
    }

    public void changeLoc(double x, double y){
        this.x = x;
        this.y = y;
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

    public void draw(Graphics2D g2d) {
        // Mentjük az eredeti transformációt
        AffineTransform old = g2d.getTransform();

        // Ellenség pozíciójának beállítása
        g2d.translate(x, y);

        // Ellenség képének forgatása és rajzolása
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 90), enemysize / 2, enemysize / 2);
        g2d.drawImage(image, tran, null);
        Shape shape = getEnemyShape();
        renderhp(g2d,shape,y);
        g2d.setTransform(old);/*

        g2d.setColor(Color.RED);
        g2d.draw(shape);    */          // Körvonalat rajzolunk

        // Eredeti transformáció visszaállítása

    }


    public boolean check(int width, int height){
        Rectangle size = getEnemyShape().getBounds();
        if(x<=-size.getWidth() ||y<= -size.getHeight() || x > width || y > height){
            return false;
        }else{
            return true;
        }
    }

    public Area getEnemyShape(){
        AffineTransform tf = new AffineTransform();
        tf.translate(x,y);
        tf.rotate(Math.toRadians(angle),size/2,size/2);
        return new Area(tf.createTransformedShape(enemyShape));
    }
}
