package main;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String args[]){
        JFrame win = new JFrame();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setResizable(false);
        //win.setBackground(Color.black);
        win.setTitle("Eldoria");

        GamePanel gpanel = new GamePanel();
        win.add(gpanel);

        win.pack();

        win.setLocationRelativeTo(null);
        win.setVisible(true);
        gpanel.setupStuff();
        gpanel.startGameThread();

    }
}
