package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_chest extends Sobject{

    public obj_chest(){

        name = "Cofre";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Chest0.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
