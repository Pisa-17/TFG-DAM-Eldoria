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
        gp.obj[0] = new obj_key(gp);
        gp.obj[0].worldX = 35 * gp.tileSize;
        gp.obj[0].worldY = 45 * gp.tileSize;

        gp.obj[1] = new obj_key(gp);
        gp.obj[1].worldX = 20 * gp.tileSize;
        gp.obj[1].worldY = 25 * gp.tileSize;

        gp.obj[2] = new obj_chest(gp);
        gp.obj[2].worldX = 22 * gp.tileSize;
        gp.obj[2].worldY = 22 * gp.tileSize;

        gp.obj[3] = new obj_scroll(gp);
        gp.obj[3].worldX = 27 * gp.tileSize;
        gp.obj[3].worldY = 25 * gp.tileSize;

        gp.obj[3] = new obj_boots(gp);
        gp.obj[3].worldX = 22 * gp.tileSize;
        gp.obj[3].worldY = 25 * gp.tileSize;
    }
}
