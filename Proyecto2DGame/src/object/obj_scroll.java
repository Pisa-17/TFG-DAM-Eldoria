package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_scroll extends Sobject{

    public obj_scroll(){

        name = "Pergamino";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Scroll01.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
