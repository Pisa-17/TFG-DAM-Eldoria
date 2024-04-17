package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_key extends Sobject{
    public obj_key(){

        name = "Gold Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Key01.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
