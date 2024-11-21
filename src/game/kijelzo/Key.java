package game.kijelzo;

public class Key {
    private boolean turnRightkey;
    private boolean turnLeftkey;
    private boolean key_space;
    private boolean key_j;
    private boolean key_k;
    private boolean goForWard;

    public boolean isgoForWard(){
        return goForWard;
    }

    public void setGoForWard(boolean goForWard){
        this.goForWard = goForWard;
    }

    public boolean isTurnLeftkey() {
        return turnLeftkey;
    }

    public void setTurnLeftkey(boolean turnLeftkey) {
        this.turnLeftkey = turnLeftkey;
    }

    public boolean isTurnRightkey() {
        return turnRightkey;
    }

    public void setTurnRightkey(boolean turnRightkey) {
        this.turnRightkey = turnRightkey;
    }

    public boolean isKey_space() {
        return key_space;
    }

    public void setKey_space(boolean key_space) {
        this.key_space = key_space;
    }

    public boolean isKey_j() {
        return key_j;
    }

    public void setKey_j(boolean key_j) {
        this.key_j = key_j;
    }

    public boolean isKey_k() {
        return key_k;
    }

    public void setKey_k(boolean key_k) {
        this.key_k = key_k;
    }
}
