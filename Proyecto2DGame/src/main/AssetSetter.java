package main;

import object.obj_boots;
import object.obj_chest;
import object.obj_key;
import object.obj_scroll;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){

    }
    public void setNPC(){

        gp.npc[0] = new NPC_Sabio(gp);
        gp.npc[0].wordlx = gp.tileSize*21;
        gp.npc[0].wordly = gp.tileSize*21;
    }
}
