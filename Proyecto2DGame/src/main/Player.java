package main;

import object.obj_defense_scrollFire;
import object.obj_key;
import object.obj_weapon_katana;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.security.Key;
import java.util.ArrayList;

public class Player extends Entidad {
    KeyboardHandler KeyH;
    public final int ScreenX;
    public final int ScreenY;
    int standCounter = 0;
    public boolean attackCancel = false;
    public ArrayList<Entidad> inventory = new ArrayList<>();
    public int inventorySize = 20;


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
        setItems();
    }

    private void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new obj_key(gp));
        inventory.add(new obj_key(gp));
        inventory.add(new obj_key(gp));
        inventory.add(new obj_key(gp));
        inventory.add(new obj_key(gp));
        inventory.add(new obj_key(gp));
        inventory.add(new obj_key(gp));
        inventory.add(new obj_key(gp));
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
        level = 1;
        maxHP = 6;
        life = maxHP;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 10;
        coin = 0;
        currentWeapon = new obj_weapon_katana(gp);
        currentShield = new obj_defense_scrollFire(gp);
        
        attack = getAttack();
        defense = getDefense();
    }
    public int getAttack(){
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
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

            if (KeyH.enterPressed == true && attackCancel == false){
                attacking = true;
                spriteCounter = 0;
            }
            attackCancel = false;
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

                        int damage = attack - gp.monster[i].defense;
                        if (damage < 0){
                            damage = 0;
                        }
                        gp.monster[i].life -= damage;
                        gp.overlayUI.addMessage(damage + " damage!");
                        gp.monster[i].invencible =true;
                        //gp.monster[i].damageReaction();
                        if (gp.monster[i].life <= 0){
                            gp.monster[i].dying = true;
                            gp.overlayUI.addMessage("Killed the " + gp.monster[i].name + "!");
                            gp.overlayUI.addMessage("You gained  " + gp.monster[i].exp + " exp!");
                            exp += gp.monster[i].exp;
                            checkLevel();
                        }
            }
        }
    }

    private void checkLevel(){
        if (exp >= nextLevelExp){
            level ++;
            nextLevelExp = nextLevelExp*2;
            maxHP += 2;
            strength ++;
            dexterity ++;
            attack = getAttack();
            defense = getDefense();

            gp.gameState = gp.dialogueState;
            gp.overlayUI.dialogo = "You are level " + level + " !";
        }
    }
    private void contactoMonster(int i) {
        if (i != 999){
            if (invencible == false) {
                int damage = gp.monster[i].attack - defense;
                if (damage < 0){
                    damage = 0;
                }
                life -= damage;
                invencible = true;
            }
        }
    }

    private void interactionNpc(int npcIndex) {
        if(gp.KeyH.enterPressed == true){
            if (npcIndex != 999) {
                attackCancel = true;
                    gp.gameState = gp.dialogueState;
                    gp.npc[npcIndex].speak();
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
        if (dying == true){
            dyingAnimation(g2);
        }
        g2.drawImage(image, ScreenX, ScreenY, null);

        ///Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter ++;

        int i = 10;

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
}
