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

        up1 = setup("/npc/monk_up1");
        up2 = setup("/npc/monk_up2");
        down1 = setup("/npc/monk_down1");
        down2 = setup("/npc/monk_down2");
        right1 = setup("/npc/monk_right1");
        right2 = setup("/npc/monk_right2");
        left1 = setup("/npc/monk_left1");
        left2 = setup("/npc/monk_left2");

    }
    public void setDialogue(){
        dialogues[0] = "Hola aventurero!";
        dialogues[1] = "Asi que has llegado a esta \nisla por casualidad, curioso la verdad...";
        dialogues[2] = "Yo soy un monje bastante anciano\n ya, pero podria ayudarte";
        dialogues[3] = "Aunque las cosas por aqui \nno van tan bien como parece";
        dialogues[4] = "Ten buena suerte en la aventura y \nno dudes en consultarme dudas";
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
        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.overlayUI.dialogo = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.path){
            case "up":
                path = "down";
                break;
            case "down":
                path = "up";
                break;
            case "left":
                path = "right";
                break;
            case "right":
                path = "left";
                break;
        }
    }
}
