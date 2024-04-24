package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_chest extends Sobject{
    GamePanel gp;
    public obj_chest(GamePanel gp){

        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Chest0.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
