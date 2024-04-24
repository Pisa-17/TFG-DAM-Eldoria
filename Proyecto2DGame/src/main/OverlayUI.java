package main;

import object.obj_key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OverlayUI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_20;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public OverlayUI(GamePanel gp) {
        this.gp = gp;
        arial_20 = new Font("Arial", Font.PLAIN, 80);
        //obj_key key = new obj_key(gp);
        //keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawUI(Graphics2D g2) {
       this.g2 = g2;

       g2.setFont(arial_20);
       g2.setColor(Color.white);

       if (gp.gameState == gp.playState){
           // Play state stuf
       }
       if (gp.gameState == gp.pauseState){
            drawPauseScreenState();
       }
    }

    public void drawPauseScreenState(){

        String text = "Pausa";
        int x = getXforCenter(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public int getXforCenter(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenHeight/2 - length/2;
        return x;
    }
}
