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
    public BufferedImage attackUp1, attackDown1, attackLeft1, attackRight1;
    public String path = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle hitbox = new Rectangle(0,0,48,48);
    public Rectangle attackHitbox = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    public int actionLoockCounter = 0;
    public boolean invencible = false;
    public int invencibleCounter = 0;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public int hpBarCounter = 0;
    public String dialogues[] = new String[20];
    int dialogueIndex = 0;
    int dyingCounter = 0;
    public int type;

    //Character status
    public int maxHP;
    public int life;

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collisionObject = true;

        public Entidad(GamePanel gp){
            this.gp = gp;
        }
        public void setAction(){}
        public void damageReaction(){}
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
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            boolean contactoPlayer = gp.cChecker.checkPlayer(this);

            if (this.type == 2 && contactoPlayer ==true){
                if (gp.player.invencible ==false){
                    gp.player.life -=1;
                    gp.player.invencible = true;
                }
            }

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
            if (invencible == true){
                invencibleCounter++;
                if (invencibleCounter > 40){
                    invencible = false;
                    invencibleCounter = 0;
                }
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

                /// Monstrer hp bar
                if (type == 2 && hpBarOn == true){

                    double oneScale = (double)gp.tileSize/maxHP;
                    double hpBarValue = oneScale*life;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(screenX-1, screenY-16, gp.tileSize+2, 12);

                    g2.setColor(new Color(70,100,255));
                    g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);


                    hpBarCounter++;
                    if (hpBarCounter > 600){
                        hpBarCounter = 0;
                        hpBarOn = false;
                    }
                }

                if (invencible == true){
                    hpBarOn = true;
                    hpBarCounter = 0;
                    changeAlpha(g2, 0.4F);
                }
                if (dying == true){
                    dyingAnimation(g2);
                }
                g2.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize, null);
                changeAlpha(g2, 1F);
            }
        }
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter ++;

        int i = 5;

        if (dyingCounter <=i){
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i*2){
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i*2 && dyingCounter <= i*3){
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i*3 && dyingCounter <= i*4){
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i*4 && dyingCounter <= i*5){
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i*5 && dyingCounter <= i*6){
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i*6 && dyingCounter <= i*7){
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i*7 && dyingCounter <= i*8){
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i*8){
            dying = false;
            alive = false;

        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
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
