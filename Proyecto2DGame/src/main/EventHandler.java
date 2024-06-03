package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX,previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];


        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row++;

                if (row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }

    }
    public void checkEvent(){

        int xDistance = Math.abs(gp.player.wordlx - previousEventX);
        int yDistance = Math.abs(gp.player.wordly - previousEventY);
        int distance  = Math.max(xDistance, yDistance);
        if (distance> gp.tileSize){
            canTouchEvent = true;
        }

        if (canTouchEvent == true){
            if (hit(0,27,16,"right") == true){
                // Event happens
                damagePit(gp.dialogueState);
            }
            if (hit(0,37,16,"right") == true){
                // Event happens
                healingEvent(gp.dialogueState);
            }
            if (hit(0,35,3,"up") == true){
                teleport(1,3,49);
            }
            if (hit(1,3,49,"down") == true){
                teleport(0,35,3);
            }
        }


    }

    private void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.wordlx = col * gp.tileSize;
        gp.player.wordly = row * gp.tileSize;
        previousEventX = gp.player.wordlx;
        previousEventY = gp.player.wordly;
        canTouchEvent = false;

    }

    private void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.overlayUI.dialogo = "Te has hecho danio";
        gp.player.life -= 1;
    }

    public boolean hit(int map, int col, int row, String reqDirection){
       boolean hit = false;
        if (map == gp.currentMap){
            gp.player.hitbox.x = gp.player.wordlx + gp.player.hitbox.x;
            gp.player.hitbox.y = gp.player.wordly + gp.player.hitbox.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.hitbox.intersects(eventRect[map][col][row])){
                if (gp.player.path.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;
                }
            }

            gp.player.hitbox.x = gp.player.solidAreaDefaultX;
            gp.player.hitbox.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }


       return hit;
    }
    public void healingEvent(int gameState){
        if (gp.KeyH.enterPressed == true){
            gp.gameState = gameState;
            gp.player.attackCancel = true;
            gp.overlayUI.dialogo = "Has recuperado vida!";
            gp.player.life = gp.player.maxHP;
            gp.aSetter.setMonster();
        }

    }
}
