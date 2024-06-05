package main;

import monster.mon_cyclope;
import object.*;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){

        int nmap = 0;

        int i=0;
        gp.obj[nmap][0] = new obj_door(gp);
        gp.obj[nmap][0].wordlx = gp.tileSize*21;
        gp.obj[nmap][0].wordly = gp.tileSize*22;
        i++;
        gp.obj[nmap][1] = new obj_key(gp);
        gp.obj[nmap][1].wordlx = gp.tileSize*24;
        gp.obj[nmap][1].wordly = gp.tileSize*25;
        i++;
        gp.obj[nmap][2] = new obj_chest(gp);
        gp.obj[nmap][2].wordlx = gp.tileSize*23;
        gp.obj[nmap][2].wordly = gp.tileSize*24;
        i++;
        gp.obj[nmap][3] = new obj_lifepot(gp);
        gp.obj[nmap][3].wordlx = gp.tileSize*22;
        gp.obj[nmap][3].wordly = gp.tileSize*22;
        i++;
        gp.obj[nmap][4] = new obj_coin(gp);
        gp.obj[nmap][4].wordlx = gp.tileSize*19;
        gp.obj[nmap][4].wordly = gp.tileSize*19;

    }
    public void setNPC(){
        int nmap = 0;

        int i=0;
        gp.npc[nmap][0] = new NPC_Sabio(gp);
        gp.npc[nmap][0].wordlx = gp.tileSize*21;
        gp.npc[nmap][0].wordly = gp.tileSize*21;
        i++;
        gp.npc[nmap][1] = new NPC_Merchant(gp);
        gp.npc[nmap][1].wordlx = gp.tileSize*20;
        gp.npc[nmap][1].wordly = gp.tileSize*20;
    }
    public void setMonster(){
        int nmap = 0;

        gp.monster[nmap][0] = new mon_cyclope(gp);
        gp.monster[nmap][0].wordlx = gp.tileSize*25;
        gp.monster[nmap][0].wordly = gp.tileSize*25;
    }
}
