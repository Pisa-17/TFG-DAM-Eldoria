package main;

import object.obj_key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OverlayUI {
    GamePanel gp;
    Font arial_20;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public OverlayUI(GamePanel gp) {
        this.gp = gp;
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        obj_key key = new obj_key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawUI(Graphics2D g2) {
        g2.setFont(arial_20);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x: " + gp.player.tengoKey, 74, 65);

        //Mensaje
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30.0f));
            g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

            messageCounter++;

            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
}
