package game.objects;

public class HealPoints {
    private double hp_now;
    private double max_hf;

    public HealPoints(double hp_now, double max_hf) {
        this.hp_now = hp_now;
        this.max_hf = max_hf;
    }

    public double getHp_now() {
        return hp_now;
    }

    public double getMax_hf() {
        return max_hf;
    }

    public void setHp_now(double hp_now) {
        this.hp_now = hp_now;
    }

    public void setMax_hf(double max_hf) {
        this.max_hf = max_hf;
    }
}
