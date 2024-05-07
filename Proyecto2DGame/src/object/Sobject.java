package object;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Sobject {
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = true;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    UtilityTool uTool = new UtilityTool();

    public void draw(Graphics2D g2, GamePanel gp){
        int screenX = worldX - gp.player.wordlx + gp.player.ScreenX;
        int screenY = worldY - gp.player.wordly + gp.player.ScreenY;

        if (worldX + gp.tileSize > gp.player.wordlx - gp.player.ScreenX &&
            worldX - gp.tileSize < gp.player.wordlx + gp.player.ScreenX &&
            worldY + gp.tileSize > gp.player.wordly - gp.player.ScreenY &&
            worldY + gp.tileSize < gp.player.wordly + gp.player.ScreenY){
            g2.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize, null);
        }
    }
}
