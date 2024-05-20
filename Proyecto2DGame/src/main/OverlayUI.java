package main;

import object.obj_heart;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OverlayUI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_20;
    BufferedImage keyImage;
    BufferedImage heart_full, heart_half,heart_empty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public String dialogo = "";
    public int commandNum = 0;

    public OverlayUI(GamePanel gp) {
        this.gp = gp;
        arial_20 = new Font("Tahoma", Font.PLAIN, 80);

        // Create HUD Object
        Entidad heart = new obj_heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawUI(Graphics2D g2) {
       this.g2 = g2;

       g2.setFont(arial_20);
       g2.setColor(Color.white);

       // Title State
       if (gp.gameState == gp.titleState){
           drawTitleScreen();
       }

       if (gp.gameState == gp.playState){
           // Play state stuf
           drawPlayerLife();
       }
       if (gp.gameState == gp.pauseState){
           drawPlayerLife();
            drawPauseScreenState();
       }
       /// Dialogo NPC
        if (gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
        /// Status screen
        if (gp.gameState == gp.statusState){
            drawStatusScreen();
        }


    }

    private void drawStatusScreen() {
        /// Creamos el frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;

        drawLetters(frameX, frameY, frameWidth, frameHeight);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX +20;
        int textY = frameY + gp.tileSize;

        final int lineHeeight = 32;

        ///Names
        g2.drawString("Level", textX, textY);
        textY += lineHeeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeeight;
        g2.drawString("Strenght", textX, textY);
        textY += lineHeeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeeight + 15;
        g2.drawString("Protection", textX, textY);


        //Values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        value = String.valueOf(gp.player.life);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToStatus(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize + 10, textY -5, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX -gp.tileSize + 10, textY - 5, null);
    }

    private void drawPlayerLife() {

        //gp.player.life=1;
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // Draw max life
        while(i < gp.player.maxHP/2){
            g2.drawImage(heart_empty,x,y,null);
            i++;
            x +=gp.tileSize;
        }
        //Reset
         x = gp.tileSize/2;
         y = gp.tileSize/2;
         i = 0;

         //Draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if (i<gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    private void drawTitleScreen() {

        g2.setColor(new Color(180,141,244));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        // Title screen name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 92F));
        String text = "Eldoria - TFG";
        int x = getXforCenter(text) + 100;
        int y = gp.tileSize*3;

        //Sombra del titulo
        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5,y+5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Imagen de la caratula del juego
        x = gp.screenWidth/2 -(gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x,y,gp.tileSize*2,gp.tileSize*2,null);

        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "Nuevo juego";
        x = getXforCenter(text) +100;
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Cargar juego";
        x = getXforCenter(text) +100;
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Salir";
        x = getXforCenter(text)+100;
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }

    private void drawDialogueScreen() {
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawLetters(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,26F));
        x += gp.tileSize/2;
        y += gp.tileSize;
        g2.drawString(dialogo,x,y);

        for (String line : dialogo.split("\n")){
            g2.drawString(line, x, y);
            y +=40;
        }
    }

    private void drawLetters(int x, int y, int width, int height) {
        Color backGround = new Color(0,0,0, 200);
        g2.setColor(backGround);
        g2.fillRoundRect(x,y,width,height,35,35);

        backGround = new Color(255,255,255);
        g2.setColor(backGround);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public void drawPauseScreenState(){
        String text = "Pausa";
        int x = getXforCenter(text);
        int y = gp.screenHeight/2;
        g2.drawString(text,x,y);
    }

    public int getXforCenter(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenHeight/2 - length/2;
        return x;
    }
    public int getXforAlignToStatus(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
