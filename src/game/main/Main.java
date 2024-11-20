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

    public void init() {
        setTitle("Tank Game");
        setSize(1300, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
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