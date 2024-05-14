package main;

import monster.mon_cyclope;
import object.obj_chest;
import object.obj_door;
import object.obj_key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){

        gp.obj[0] = new obj_door(gp);
        gp.obj[0].wordlx = gp.tileSize*21;
        gp.obj[0].wordly = gp.tileSize*22;

        gp.obj[1] = new obj_key(gp);
        gp.obj[1].wordlx = gp.tileSize*24;
        gp.obj[1].wordly = gp.tileSize*25;

        gp.obj[2] = new obj_chest(gp);
        gp.obj[2].wordlx = gp.tileSize*23;
        gp.obj[2].wordly = gp.tileSize*24;
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
