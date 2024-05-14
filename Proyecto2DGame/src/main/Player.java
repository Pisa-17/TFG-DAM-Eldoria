package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.security.Key;

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

        attackHitbox.width = 36;
        attackHitbox.height = 36;

        setDefaultValues();
        getPlayerSpritesWalking();
        getPlayerAttackImage();
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
    public void getPlayerAttackImage(){
        attackUp1= setup("/player/attackup_player");
        attackDown1 = setup("/player/attack1_player");
        attackLeft1 = setup("/player/attackleft_player");
        attackRight1 = setup("/player/attackright_player");
    }


    public void setDefaultValues() {
        wordlx = gp.tileSize*23;
        wordly = gp.tileSize*21;
        speed = 4;
        path = "down";

        //Player status
        maxHP = 6;
        life = maxHP;
    }

    public void update() {
        if (attacking == true){
            attacking();
        }
        else if (KeyH.upPressed == true || KeyH.downPreseed == true || KeyH.rightPressed == true || KeyH.leftPressed == true || KeyH.enterPressed == true) {
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

            /// Check monster colision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactoMonster(monsterIndex);

            ///Check the events
            gp.eHandler.checkEvent();



            ///Si la colision es falsa
            if (collision == false && KeyH.enterPressed == false) {
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
            gp.KeyH.enterPressed = false;
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
        /// Esto tiene que estar aqui
        if (invencible == true){
            invencibleCounter++;
            if (invencibleCounter > 60){
                invencible = false;
                invencibleCounter = 0;
            }
        }
    }

    private void attacking() {
        spriteCounter ++;

        if (spriteCounter <= 5){
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 1;

            /// Guardamos coordenadas de la hitbox
            int currentWorldX = wordlx;
            int currentWorldY = wordly;
            int hitboxWidth = hitbox.width;
            int hitboxHeight = hitbox.height;
            switch (path){
                case "up": wordly -= attackHitbox.height; break;
                case "down": wordly += attackHitbox.height; break;
                case "left": wordlx -= attackHitbox.width; break;
                case "right": wordlx += attackHitbox.width; break;
            }
            hitbox.width = attackHitbox.width;
            hitbox.height = attackHitbox.height;
            /// Si tuvieramos mas de un sprite o un sprite de 32x16 deberiamos mantener esas variables, pero las dejamos en el codigo para futuras versiones
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);
            /// Si continuaramos lo dicho con animaciones de 32x16 deberiamos resetear los valores sobreescritos en caso de conflictos
            wordlx = currentWorldX;
            wordly = currentWorldY;
            hitbox.width = hitboxWidth;
            hitbox.height = hitboxHeight;

        }


        if (spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void damageMonster(int i) {
        if ( i != 999) {
            if (gp.monster[i].invencible == false){
                        gp.monster[i].life -=1;
                        gp.monster[i].invencible =true;
                        if (gp.monster[i].life <= 0){
                            gp.monster[i] = null;
                        }
            }
        }
    }

    private void contactoMonster(int i) {
        if (i != 999){
            if (invencible == false) {
                life -= 1;
                invencible = true;
            }
        }
    }

    private void interactionNpc(int npcIndex) {
        if(gp.KeyH.enterPressed == true){
            if (npcIndex != 999) {
                    gp.gameState = gp.dialogueState;
                    gp.npc[npcIndex].speak();
            }
            else {
                attacking = true;
            }
        }
    }

    public void recogerObjeto(int index) {
        if (index != 999) {

        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (path) {
            case "up":
                if (attacking == false){
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking == true){
                    image = attackUp1;
                }
                break;
            case "down":
                if (attacking == false){
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking == true){
                    image = attackDown1;
                }
                break;
            case "left":
                if (attacking == false){
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking == true){
                    image = attackLeft1;
                }
                break;
            case "right":
                if (attacking == false){
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking == true){
                    image = attackRight1;
                }
                break;
        }
        if (invencible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, ScreenX, ScreenY, null);

        ///Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
