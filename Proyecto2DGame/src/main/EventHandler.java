package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
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

    public boolean hit(int eventCol, int eventRow, String reqDirection){
       boolean hit = false;

       gp.player.hitbox.x = gp.player.wordlx + gp.player.hitbox.x;
       gp.player.hitbox.y = gp.player.wordly + gp.player.hitbox.y;
       eventRect.x = eventCol*gp.tileSize + eventRect.x;
       eventRect.y = eventRow*gp.tileSize + eventRect.y;

       if (gp.player.hitbox.intersects(eventRect)){
           if (gp.player.path.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
               hit = true;
           }
       }

        gp.player.hitbox.x = gp.player.solidAreaDefaultX;
        gp.player.hitbox.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

       return hit;
    }
    public void healingEvent(int gameState){
        if (gp.KeyH.enterPressed == true){
            gp.gameState = gameState;
            gp.overlayUI.dialogo = "Has recuperado vida!";
            gp.player.life = gp.player.maxHP;
        }

    }
}
