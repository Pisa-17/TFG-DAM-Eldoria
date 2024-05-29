package monster;

import main.Entidad;
import main.GamePanel;
import object.obj_coin;

import java.util.Random;

public class mon_cyclope extends Entidad {
    GamePanel gp;

    public mon_cyclope(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Cyclope";
        speed = 2;
        maxHP = 20;
        life = maxHP;
        attack = 5;
        defense = 0;
        exp = 2;

        hitbox.x = 3;
        hitbox.y = 18;
        hitbox.width=42;
        hitbox.height = 30;
        solidAreaDefaultX = hitbox.x;
        solidAreaDefaultY = hitbox.y;

        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/up1_cyclope", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/up2_cyclope", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/monster1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/monster2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/right1_cyclope", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/right2_cyclope", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/left1_cyclope", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/left2_cyclope", gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        actionLoockCounter++;
        if (actionLoockCounter == 75){
            Random random  = new Random();
            int i = random.nextInt(100)+1;


            if (i <= 25){
                path = "up";
            }
            if (i > 25 && i <= 35){
                path = "down";
            }
            if (i > 35 && i <=45){
                path = "left";
            }
            if (i > 45 && i <=75){
                path = "right";
            }
            actionLoockCounter = 0;
        }
    }
    public void damageReaction(){
        actionLoockCounter = 0;
        path = gp.player.path;
    }
    public void checkDrop(){
        droppedItem(new obj_coin(gp));
    }
}
