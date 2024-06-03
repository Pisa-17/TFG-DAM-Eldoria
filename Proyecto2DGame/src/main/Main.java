package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static JFrame win;
    public static void main(String args[]){
        win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);
        win.setTitle("Eldoria");

        try {
            Image icon = ImageIO.read(Main.class.getResource("/player/icono.jpg"));
            win.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        GamePanel gpanel = new GamePanel();
        win.add(gpanel);

        gpanel.config.loadConfig();
        if (gpanel.fullScreenOn == true){
            win.setUndecorated(true);
        }

        win.pack();

        win.setLocationRelativeTo(null);
        win.setVisible(true);
        gpanel.setupStuff();
        gpanel.startGameThread();

    }
}
