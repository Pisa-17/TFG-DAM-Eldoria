package main;

import object.obj_boots;
import object.obj_key;
import object.obj_lifepot;
import object.obj_weapon_rapier;




public class NPC_Merchant extends Entidad{

    public NPC_Merchant(GamePanel gp) {
        super(gp);

        path = "down";
        speed = 1;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {

        up1 = setup("/npc/merchantup1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/merchantup2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/merchantdown1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/merchantdown2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/merchantright1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/merchantright2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/merchantleft1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/merchantleft2", gp.tileSize, gp.tileSize);

    }
    public void setDialogue() {
        dialogues[0] = "Hola me has encontrado!, Soy el mercader";
    }
    public void setItems(){
        inventory.add(new obj_lifepot(gp));
        inventory.add(new obj_weapon_rapier(gp));
        inventory.add(new obj_key(gp));
        inventory.add(new obj_boots(gp));
        inventory.add(new obj_key(gp));
    }

    public void speak(){
        super.speak();
    }
}
