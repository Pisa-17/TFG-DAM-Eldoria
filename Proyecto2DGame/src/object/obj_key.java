package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_key extends Sobject{
    GamePanel gp;
    public obj_key(GamePanel gp){

        name = "Gold Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Key01.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
