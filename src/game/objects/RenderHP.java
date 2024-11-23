package game.objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RenderHP {
    private final HealPoints hp;

    public RenderHP(HealPoints hp) {
        this.hp = hp;
    }

    protected void renderhp(Graphics2D g2d, Shape shape, double y) {
        if(hp.getHp_now()!=hp.getMax_hf()) {
            double hpY = shape.getBounds().getY() - y;
            g2d.setColor(new Color(70, 70, 70));
            g2d.fill(new Rectangle2D.Double(0, hpY, Enemy.enemysize / 2, 5));
            g2d.setColor(new Color(250,90,90));
            double sizeofHP = hp.getHp_now() / hp.getMax_hf() * Enemy.enemysize/2;
            g2d.fill(new Rectangle2D.Double(0, hpY, sizeofHP, 5));
        }
    }

    public boolean updateHP(double Minus){
        hp.setHp_now(hp.getHp_now()-Minus);
        return hp.getHp_now() > 0;
    }

    public double getHP(){
        return hp.getHp_now();
    }

    public void resetHP(){
        hp.setHp_now(hp.getMax_hf());
    }

}
