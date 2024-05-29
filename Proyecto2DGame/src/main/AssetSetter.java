package main;

import monster.mon_cyclope;
import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){

        int i=0;
        gp.obj[0] = new obj_door(gp);
        gp.obj[0].wordlx = gp.tileSize*21;
        gp.obj[0].wordly = gp.tileSize*22;
        i++;
        gp.obj[1] = new obj_key(gp);
        gp.obj[1].wordlx = gp.tileSize*24;
        gp.obj[1].wordly = gp.tileSize*25;
        i++;
        gp.obj[2] = new obj_chest(gp);
        gp.obj[2].wordlx = gp.tileSize*23;
        gp.obj[2].wordly = gp.tileSize*24;
        i++;
        gp.obj[3] = new obj_lifepot(gp);
        gp.obj[3].wordlx = gp.tileSize*22;
        gp.obj[3].wordly = gp.tileSize*22;
        i++;
        gp.obj[4] = new obj_coin(gp);
        gp.obj[4].wordlx = gp.tileSize*19;
        gp.obj[4].wordly = gp.tileSize*19;

    }
    public void setNPC(){

        gp.npc[0] = new NPC_Sabio(gp);
        gp.npc[0].wordlx = gp.tileSize*21;
        gp.npc[0].wordly = gp.tileSize*21;
    }
    public void setMonster(){
        gp.monster[0] = new mon_cyclope(gp);
        gp.monster[0].wordlx = gp.tileSize*25;
        gp.monster[0].wordly = gp.tileSize*25;
    }
}
