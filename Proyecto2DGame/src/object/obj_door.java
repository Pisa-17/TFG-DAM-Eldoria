package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_door extends Sobject{
    GamePanel gp;
    public obj_door(GamePanel gp){

        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Door01.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
