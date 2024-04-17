package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entidad {
    GamePanel gp;
    KeyboardHandler KeyH;

    public final int ScreenX;
    public final int ScreenY;
    int tengoKey = 0;

    public Player(GamePanel gp, KeyboardHandler KeyH) {
        this.gp = gp;
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
    /// Este metodo se encarga de cargar los sprites del jugador que se usaran en otro metodo
    public void getPlayerSpritesWalking() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/character_Up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/character_Up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/character_Down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/character_Down2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/character_Right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/character_Right2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/character_Left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/character_Left2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        wordlx = 610;
        wordly = 2150;
        speed = 4;
        path = "down";
    }
    /// Este metodo actualiza el movimiento del jugador
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
    /// Este metodo se encarga de recoger objetos y contabilizarlos
    public void recogerObjeto(int index) {
        if (index != 999) {
            String objecName = gp.obj[index].name;

            switch (objecName) {
                case "Gold Key":
                    //gp.playSE(2);
                    tengoKey++;
                    gp.obj[index] = null;
                    gp.overlayUI.showMessage("Has recogido una llave");
                    break;
                case "Door":
                    if (tengoKey > 0) {
                        tengoKey--;
                        gp.obj[index] = null;
                    }
                    break;
                case "Botas":
                    speed += 2;
                    gp.obj[index] = null;
                    break;

            }

        }
    }
    /// Metodo que dibuja el personaje
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
        g2.drawImage(image, ScreenX, ScreenY, gp.tileSize, gp.tileSize, null);
    }
}
