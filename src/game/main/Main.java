package game.main;

import game.kijelzo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {

    public Main(){
        init();
    }

    /**
     * Inicializálja az alkalmazás főablakát és annak alapvető beállításait.
     * Beállítja az ablak címét, méretét, pozícióját, valamint hozzáadja a
     * játék megjelenítéséért felelős komponenst.
     * Az ablak megnyitásakor elindítja a játék logikáját.
     */
    public void init() {
        setTitle("Tank Game");
        setSize(1300, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Kijelzo kijelzo = new Kijelzo();
        add(kijelzo);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                kijelzo.start();
            }
        });
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}