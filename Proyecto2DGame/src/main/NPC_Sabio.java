package main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_Sabio extends Entidad{

    public NPC_Sabio(GamePanel gp) {
        super(gp);

        path = "down";
        speed = 1;

        getImage();
    }

    public void getImage() {

        up1 = setup("/npc/monk_up1");
        up2 = setup("/npc/monk_up2");
        down1 = setup("/npc/monk_down1");
        down2 = setup("/npc/monk_down2");
        right1 = setup("/npc/monk_right1");
        right2 = setup("/npc/monk_right2");
        left1 = setup("/npc/monk_left1");
        left2 = setup("/npc/monk_left2");

    }

    @Override
    public void setAction() {
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
