package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entidad {
    public int wordlx,wordly;
    public int speed;
    public BufferedImage up1,up2,down1,down2,right1,right2,left1,left2;
    public String path;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle hitbox;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;


}
