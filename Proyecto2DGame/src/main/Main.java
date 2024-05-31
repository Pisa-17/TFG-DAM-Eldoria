package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static JFrame win;
    public static void main(String args[]){
        win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);
        win.setTitle("Eldoria");


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
