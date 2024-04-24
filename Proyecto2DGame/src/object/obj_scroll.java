package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_scroll extends Sobject{
    GamePanel gp;
    public obj_scroll(GamePanel gp){

        name = "Pergamino";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Scroll01.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
