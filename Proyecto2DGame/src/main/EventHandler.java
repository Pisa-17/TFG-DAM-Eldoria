package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if (col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

    }
    public void checkEvent(){
        if (hit(27,16,"right") == true){
            // Event happens
            damagePit(gp.dialogueState);
        }
        if (hit(37,16,"right") == true){
            // Event happens
            healingEvent(gp.dialogueState);
        }
    }

    private void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.overlayUI.dialogo = "Te has hecho danio";
        gp.player.life -= 1;
    }

    public boolean hit(int col, int row, String reqDirection){
       boolean hit = false;

       gp.player.hitbox.x = gp.player.wordlx + gp.player.hitbox.x;
       gp.player.hitbox.y = gp.player.wordly + gp.player.hitbox.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

       if (gp.player.hitbox.intersects(eventRect[col][row])){
           if (gp.player.path.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
               hit = true;
           }
       }

        gp.player.hitbox.x = gp.player.solidAreaDefaultX;
        gp.player.hitbox.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

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
