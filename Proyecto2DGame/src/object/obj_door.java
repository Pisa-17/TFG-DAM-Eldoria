package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_door extends Sobject{

    public obj_door(){

        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Door01.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;

    }
}
