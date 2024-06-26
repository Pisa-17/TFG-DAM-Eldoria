package main;

import object.obj_heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;


public class OverlayUI {
    GamePanel gp;
    Graphics2D g2;
    Font blacksword;
    BufferedImage keyImage;
    BufferedImage heart_full, heart_half,heart_empty;
    public boolean messageOn = false;
    //public String message = "";
    //int messageCounter = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String dialogo = "";
    public int commandNum = 0;
    public int playerslotCol = 0;
    public int playerslotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    public Entidad npc;

    public OverlayUI(GamePanel gp) {
        this.gp = gp;
        try{
            InputStream is = getClass().getResourceAsStream("/fonts/YIKES!__.TTF");
            blacksword = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch (IOException e){
            e.printStackTrace();
        }catch (FontFormatException e){
            e.printStackTrace();
        }


        // Create HUD Object
        Entidad heart = new obj_heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);

    }

    public void drawUI(Graphics2D g2) {
       this.g2 = g2;

       g2.setFont(blacksword);
       g2.setColor(Color.white);

       // Title State
       if (gp.gameState == gp.titleState){
           drawTitleScreen();
       }

       if (gp.gameState == gp.playState){
           // Play state stuf
           drawPlayerLife();
           drawMessageScroll();
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
            drawInventory(gp.player, true);
        }
        /// Options screen
        if (gp.gameState == gp.optionsState){
            drawOptionScreen();
        }
        //// Game over screen
        if (gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        //// Trade screen



    }

    public ArrayList<String> getObjectsFromDatabase() {
        ArrayList<String> objects = new ArrayList<>();
        try {
            String url = "jdbc:mysql://localhost:3306/Eldoria";
            String user = "root";
            String password = "";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            String sql = "SELECT name, existencias, precio FROM objects";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String objectName = rs.getString("name");
                int existencias = rs.getInt("existencias");
                double precio = rs.getDouble("precio");

                String objectInfo = "Nombre: " + objectName + ", Existencias: " + existencias + ", Precio: " + precio;
                objects.add(objectInfo);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }
        return objects;
    }


    private void drawTradeScreen() {
        switch (subState){
            case 1: trade_select();break;
            case 2: trade_buy();break;
            case 3: trade_sell();break;
        }
        gp.KeyH.enterPressed = false;
    }
    public void trade_select(){
        drawDialogueScreen();

        // options window
        int x = gp.tileSize *15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize*3;
        int height = (int)(gp.tileSize *3.5);
        drawLetters(x,y,width,height);

        // draw texts
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Comprar", x,y);
        if (commandNum == 0){
            g2.drawString(">", x-24,y);
            if (gp.KeyH.enterPressed == true){
                subState = 1;
            }
        }
        y+= gp.tileSize;
        g2.drawString("Vender", x,y);
        if (commandNum == 1){
            g2.drawString(">", x-24,y);
            if (gp.KeyH.enterPressed == true){
                subState = 2;
            }
        }
        y+= gp.tileSize;
        g2.drawString("Salir", x,y);
        if (commandNum == 2){
            g2.drawString(">", x-24,y);
            if (gp.KeyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.playState;
            }
        }

    }
    public void trade_buy(){
        drawInventory(gp.player, false);
        drawInventory(npc, true);
        //NPC
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawLetters(x,y,width,height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //Player
         x = gp.tileSize*12;
         y = gp.tileSize*9;
         width = gp.tileSize*6;
         height = gp.tileSize*2;
        drawLetters(x,y,width,height);
        g2.drawString("Dinero: " + gp.player.coin, x+24, y+60);

        //Precio
        int itemIndex = getIndexOfCursor(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()){
            x = (int) (gp.tileSize*5.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) gp.tileSize;
            height = gp.tileSize;
            drawLetters(x,y,width,height);

            int price = npc.inventory.get(itemIndex).price;
            String text = "Precio: " + price;
            x = getXforCenter(text) + 200;
            g2.drawString(text, x, y+60);
        }
        // Buy an item
        if (gp.KeyH.enterPressed == true){
            itemIndex = getIndexOfCursor(npcSlotCol, npcSlotRow);
            if (itemIndex < npc.inventory.size()){
                if (gp.player.coin >= npc.inventory.get(itemIndex).price){
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                    npc.inventory.remove(itemIndex);
                }
                else {
                    addMessage("No tienes suficiente dinero");
                }
            }
        }

    }
    public void trade_sell(){
        // FUTURE UPDATE
    }

    private void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Has muerto!";
        g2.setColor(Color.BLACK);
        x = getXforCenter(text) + 200;
        y = gp.tileSize*4;
        g2.drawString(text,x,y);

        g2.setColor(Color.white);
        g2.drawString(text,x-4,y-4);

        //Retry option
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Reintentar";
        x = getXforCenter(text) +170;
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.drawString(">", x-40,y);
        }
        /// Back to title screen
        text = "Salir";
        x = getXforCenter(text) +170;
        y +=55;
        g2.drawString(text,x,y);
        if (commandNum == 1){
            g2.drawString(">", x-40,y);
        }
    }

    private void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Sub window/panel
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;

        drawLetters(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0: option_Top(frameX,frameY);break;
            case 1: options_fullScreen(frameX, frameY); break;
            case 2: options_endGame(frameX, frameY);break;
        }

        gp.KeyH.enterPressed = false;

    }

    private void options_endGame(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        dialogo = "Volver al menu\n principal";

        for (String line: dialogo.split("\n")){
            g2.drawString(line, textX, textY);
            textY +=40;
        }

        //YES
        String text = "Si";
        textX = getXforCenter(text)+150;
        textY += gp.tileSize*3;
        g2.drawString(text, textX,textY);
        if (commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed == true){
                subState = 0;
                gp.gameState = gp.titleState;
            }
        }
        //NO
        text = "No";
        textX = getXforCenter(text)+200;
        g2.drawString(text, textX,textY);
        if (commandNum == 1){
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed == true){
                subState = 0;
                commandNum = 2;
            }
        }

    }

    public void option_Top(int frameX, int frameY){
        int textX;
        int textY;

        //Titulo
        String text = "Opciones";
        textX = getXforCenter(text) + 200;
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        // FULL SCREEN ON/OFF SETTING
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Pantalla completa", textX,textY);
        if (commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed == true){
                if (gp.fullScreenOn == false){
                    gp.fullScreenOn = true;
                }
                else if (gp.fullScreenOn == true){
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }

        }

        //Music on/off
        textY += gp.tileSize;
        g2.drawString("Musica", textX,textY);
        if (commandNum == 1) {
            g2.drawString(">", textX-25, textY);
        }

        //Endgame
        textY += gp.tileSize;
        g2.drawString("Salir", textX,textY);
        if (commandNum == 2) {
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed == true){
                subState = 2; // Cambia a submenú de fin de juego
                gp.KeyH.enterPressed = false; // Reinicia el estado de la tecla 'Enter'
            }
        }

        //Volver
        textY += gp.tileSize*2;
        g2.drawString("Volver", textX,textY);
        if (commandNum == 3) {
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed == true){
                subState = 0;
                gp.gameState = gp.playState;
            }
        }

        /// Check box full screen
        textX = frameX + gp.tileSize*5 + 75;
        textY = frameY + gp.tileSize*2 - 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,24,24);
        if (gp.fullScreenOn == true){
            g2.fillRect(textX, textY, 24,24);
        }

        //Check Box Music
        textX = frameX + gp.tileSize*5;
        textY = frameY + gp.tileSize*2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,24*5,24);

        g2.drawRect(textX,textY,120,24);
        int volumeWidth = 24*gp.musica.volumeScale;
        g2.fillRect(textX,textY,volumeWidth,24);

        // Check box End
        textX = frameX + gp.tileSize*5;
        textY = frameY + gp.tileSize*2 + 24*3;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX,textY,24,24);

        gp.config.saveConfig();

    }
    public void options_fullScreen(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        dialogo = "Reinicia para cambiarlo";
        g2.drawString(dialogo, textX, textY);

        textY += gp.tileSize*6;
        g2.drawString("Salir", textX,textY);
        if (commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if (gp.KeyH.enterPressed == true){
                subState = 0;
            }
        }
    }

    private void drawInventory(Entidad entity, boolean cursor) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotRow = 0;
        int slotCol = 0;
        if (entity == gp.player){
             frameX = gp.tileSize * 12;
             frameY = gp.tileSize;
             frameWidth = gp.tileSize * 6;
          frameHeight = gp.tileSize * 5;
          slotCol = playerslotCol;
          slotRow = playerslotRow;
        }
        else {
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }






        drawLetters(frameX, frameY, frameWidth, frameHeight);
        //Slots
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        for (int i = 0; i<entity.inventory.size(); i++){
            if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield){
                g2.setColor(new Color(240, 190,90));
                g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            slotX += gp.tileSize;
            if (slotX >= slotXStart + gp.tileSize * 5) { // Reset to start of next row after 5 columns
                slotX = slotXStart;
                slotY += gp.tileSize;
            }
        }

        ///Cursor
        if (cursor == true){
            int cursorX = slotXStart + (gp.tileSize * slotRow);
            int cursorY = slotYStart + (gp.tileSize * slotCol);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            //Data cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize*3;
            drawLetters(dFrameX,dFrameY,dFrameWidth,dFrameHeight);

            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getIndexOfCursor(slotCol, slotRow);

            if (itemIndex >= 0 && itemIndex < entity.inventory.size()) {
                g2.drawString(entity.inventory.get(itemIndex).description, textX, textY);
            }
        }


    }
    public int getIndexOfCursor(int slotCol, int slotRow){
        int itemIndex = slotRow + (slotCol*5);
        return itemIndex;
    }

    private void drawMessageScroll() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*5;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i<message.size(); i++){
            if (message.get(i) != null){
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX+2, messageY+2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY +=50;

                if (messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }

    private void drawStatusScreen() {
        /// Creamos el frame
        final int frameX = gp.tileSize*2;
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
        String text = "Eldoria - Proyecto FP";
        int x = getXforCenter(text) + 200;
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
        x = getXforCenter(text) +200;
        y += gp.tileSize*4;
        g2.drawString(text,x,y);
        if (commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Cargar partida (F.U)";
        x = getXforCenter(text) +200;
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "Salir";
        x = getXforCenter(text)+200;
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
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 2;
        int width = gp.tileSize*16;
        int height = (int)(gp.tileSize *7.5);
        drawLetters(x, y, width, height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,79F));
        x += gp.tileSize + 200;
        y += gp.tileSize + 20;
        g2.drawString(text,x,y);

        g2.setFont(g2.getFont().deriveFont(32F));

        ArrayList<String> objects = getObjectsFromDatabase();
        System.out.println(objects);
        y += gp.tileSize * 2;
        x = gp.tileSize * 2;



        for (String object : objects) {
            g2.drawString(object, x+15, y);
            y += gp.tileSize;
        }

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
