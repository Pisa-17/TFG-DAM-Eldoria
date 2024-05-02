package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Player extends Entidad {
    KeyboardHandler KeyH;
    public final int ScreenX;
    public final int ScreenY;
    int standCounter = 0;


    public Player(GamePanel gp, KeyboardHandler KeyH) {
        super(gp);

        this.KeyH = KeyH;

        ScreenX = gp.screenWidth / 2 - gp.tileSize / 2;
        ScreenY = gp.screenHeight / 2 - gp.tileSize / 2;

        hitbox = new Rectangle();
        hitbox.x = 8;
        hitbox.y = 16;
        solidAreaDefaultX = hitbox.x;
        solidAreaDefaultY = hitbox.y;
        hitbox.width = 32;
        hitbox.height = 32;

        setDefaultValues();
        getPlayerSpritesWalking();
    }

    public void getPlayerSpritesWalking() {

        up1 = setup("/player/ninja_up1");
        up2 = setup("/player/ninja_up2");
        down1 = setup("/player/ninja_down1");
        down2 = setup("/player/ninja_down2");
        right1 = setup("/player/ninja_right1");
        right2 = setup("/player/ninja_right2");
        left1 = setup("/player/ninja_left1");
        left2 = setup("/player/ninja_left2");

    }


    public void setDefaultValues() {
        wordlx = 610;
        wordly = 2150;
        speed = 4;
        path = "down";
    }

    public void update() {
        if (KeyH.upPressed == true || KeyH.downPreseed == true || KeyH.rightPressed == true || KeyH.leftPressed == true) {
            if (KeyH.upPressed) {
                path = "up";

            } else if (KeyH.downPreseed) {
                path = "down";

            } else if (KeyH.leftPressed) {
                path = "left";

            } else if (KeyH.rightPressed) {
                path = "right";

            }

            ///Check de colisiones
            collision = false;
            gp.cChecker.checkTitle(this);

            ////Check de la colision de objeto
            int objIndex = gp.cChecker.checkObj(this, true);
            recogerObjeto(objIndex);

            /// Check de la colision del NPC
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactionNpc(npcIndex);

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
    }

    private void interactionNpc(int npcIndex) {
        if (npcIndex != 999) {
            if (gp.KeyH.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[npcIndex].speak();
            }
        }
        gp.KeyH.enterPressed = false;
    }

    public void recogerObjeto(int index) {
        if (index != 999) {

        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
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
        g2.drawImage(image, ScreenX, ScreenY, null);
    }
}
