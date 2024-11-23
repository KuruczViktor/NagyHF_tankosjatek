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
            g2d.setColor(new Color(200, 100, 0));
            g2d.fill(new Rectangle2D.Double(0, hpY, Enemy.enemysize / 2, 5));
            g2d.setColor(new Color(250,100,100));
            double sizeofHP = hp.getHp_now() / hp.getMax_hf() * Enemy.enemysize;
            g2d.fill(new Rectangle2D.Double(0, hpY, sizeofHP, 5));
        }
    }

/*
    protected void renderhp(Graphics2D g2d, Shape shape, double yOffset) {
        // Az ellenség alakzatának méretei
        Rectangle2D bounds = shape.getBounds2D();

        // Az ellenség bal felső sarkának pozíciója
        double hpBarX = bounds.getX();  // Bal felső sarok X koordináta
        double hpBarY = bounds.getY() - yOffset; // A HP sáv Y koordinátája az alakzat felett

        // HP sáv teljes szélessége és aktuális mérete
        double hpBarWidth = MyTank.tankSize;
        double hpCurrentWidth = hp.getHp_now() / hp.getMax_hf() * MyTank.tankSize;

        // Háttér HP sáv (üres rész)
        g2d.setColor(new Color(200, 100, 0));
        g2d.fill(new Rectangle2D.Double(hpBarX, hpBarY, hpBarWidth, 2));

        // Aktuális HP (kitöltött rész)
        g2d.setColor(Color.GREEN);
        g2d.fill(new Rectangle2D.Double(hpBarX, hpBarY, hpCurrentWidth, 2));
    }
*/
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
