package monster;

import main.Entidad;
import main.GamePanel;

import java.util.Random;

public class mon_cyclope extends Entidad {
    public mon_cyclope(GamePanel gp) {
        super(gp);

        type = 2;
        name = "Cyclope";
        speed = 3;
        maxHP = 5;
        life = maxHP;

        hitbox.x = 3;
        hitbox.y = 18;
        hitbox.width=42;
        hitbox.height = 30;
        solidAreaDefaultX = hitbox.x;
        solidAreaDefaultY = hitbox.y;

        getImage();
    }
    public void getImage(){
        down1 = setup("/monster/monster1");
        down2 = setup("/monster/monster2");
        left1 = setup("/monster/left1_cyclope");
        left2 = setup("/monster/left2_cyclope");
        right1 = setup("/monster/right1_cyclope");
        right2 = setup("/monster/right2_cyclope");
        up1 = setup("/monster/up1_cyclope");
        up2 = setup("/monster/up2_cyclope");

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
}
