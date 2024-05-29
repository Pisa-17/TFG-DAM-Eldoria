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
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/npc/monk_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/monk_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/monk_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/monk_down2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/monk_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/monk_right2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/monk_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/monk_left2", gp.tileSize, gp.tileSize);

    }
    public void setDialogue(){
        dialogues[0] = "Hola aventurero!";
        dialogues[1] = "Eres muy curioso...";
        dialogues[2] = "Yo soy un monje";
        dialogues[3] = "Aunque las cosas por aqui...";
        dialogues[4] = "Ten buena suerte en la aventura";
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
    public void speak(){
        super.speak();
    }
}
