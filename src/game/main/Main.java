package game.main;

import game.kijelzo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    // Két panel közötti váltáshoz, több különböző nézetet egy komponensben kezel
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;
    private JLabel messageLabel;

    public Main() {
        init();
    }

    /**
     * Inicializálja az alkalmazás főablakát.
     */
    public void init() {
        setTitle("Tank Game");
        setSize(1300, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(cardLayout);
        // Menü létrehozása
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        Kijelzo kijelzo = new Kijelzo();
        mainPanel.add(kijelzo, "Game");

        add(mainPanel);
    }

    /**
     * Létrehozza a menüt.
     */
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());

        // Cím a tetején
        JLabel titleLabel = new JLabel("Welcome to Tank Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        // Gombok panelje középen, rácsos elrendezést tesz lehetővé, így tudtam elhelyezni a 3 gombot egymás alatt.
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));

        JButton escapeButton = createLargeButton("Escape");
        escapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("Nemnem!");
            }
        }
        );
        buttonPanel.add(escapeButton);

        // Dummy gomb
        JButton dummyButton = createLargeButton("Dummy");
        buttonPanel.add(dummyButton);

        // Start gomb
        JButton startButton = createLargeButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Game");
                Kijelzo kijelzo = (Kijelzo) mainPanel.getComponent(1);
                kijelzo.start();
            }
        }
        );
        buttonPanel.add(startButton);

        /**
         * Az escape button hatására kiírja: "Nemnem!"
         */
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuPanel.add(messageLabel, BorderLayout.SOUTH);

        // Gombok hozzáadása
        menuPanel.add(buttonPanel, BorderLayout.CENTER);
        return menuPanel;
    }

    /**
     * Nagyobb gombokat hozok létre.
     */
    private JButton createLargeButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(300, 100));
        return button;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
}