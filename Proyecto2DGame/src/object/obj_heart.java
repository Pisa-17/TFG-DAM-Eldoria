package object;

import main.Entidad;
import main.GamePanel;

public class obj_heart extends Entidad {

    public obj_heart(GamePanel gp){
        super(gp);
        name= "Heart";
        image = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_empty");
    }



}
