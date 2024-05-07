package main;

import javax.imageio.ImageIO;
import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entidad {
    GamePanel gp;
    public int wordlx,wordly;
    public int speed;
    public BufferedImage up1,up2,down1,down2,right1,right2,left1,left2;
    public String path;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle hitbox = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public int actionLoockCounter = 0;
    public String dialogues[] = new String[20];
    int dialogueIndex = 0;

        public Entidad(GamePanel gp){
            this.gp = gp;
        }
        public void setAction(){}
        public void speak(){
            if (dialogues[dialogueIndex] == null){
                dialogueIndex = 0;
            }
            gp.overlayUI.dialogo = dialogues[dialogueIndex];
            dialogueIndex++;

            switch (gp.player.path){
                case "up":
                    path = "down";
                    break;
                case "down":
                    path = "up";
                    break;
                case "left":
                    path = "right";
                    break;
                case "right":
                    path = "left";
                    break;
            }
        }
        public void update(){
            setAction();

            collision = false;
            gp.cChecker.checkTitle(this);
            gp.cChecker.checkObj(this, false);
            gp.cChecker.checkPlayer(this);

            ///Si la colision es falsa
            if (collision == false) {
                switch (path) {
                    case "up":
                        wordly -= speed;
                        break;
                    case "down":
                        wordly += speed;
                        break;
                    case "left":
                        wordlx -= speed;
                        break;
                    case "right":
                        wordlx += speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }
        public void draw(Graphics2D g2){
            BufferedImage image = null;
            int screenX = wordlx - gp.player.wordlx + gp.player.ScreenX;
            int screenY = wordly - gp.player.wordly + gp.player.ScreenY;

            if (wordlx + gp.tileSize > gp.player.wordlx - gp.player.ScreenX &&
                    wordly - gp.tileSize < gp.player.wordlx + gp.player.ScreenX &&
                    wordly + gp.tileSize > gp.player.wordly - gp.player.ScreenY &&
                    wordly + gp.tileSize < gp.player.wordly + gp.player.ScreenY){

                switch (path) {
                    case "up":
                        if (spriteNum == 1) {
                            image = up1;
                        }
                        if (spriteNum == 2) {
                            image = up2;
                        }
                        break;
                    case "down":
                        if (spriteNum == 1) {
                            image = down1;
                        }
                        if (spriteNum == 2) {
                            image = down2;
                        }
                        break;
                    case "left":
                        if (spriteNum == 1) {
                            image = left1;
                        }
                        if (spriteNum == 2) {
                            image = left2;
                        }
                        break;
                    case "right":
                        if (spriteNum == 1) {
                            image = right1;
                        }
                        if (spriteNum == 2) {
                            image = right2;
                        }
                        break;
                }
                g2.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize, null);
            }
        }

    public BufferedImage setup(String imagePath ){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream( imagePath +".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
