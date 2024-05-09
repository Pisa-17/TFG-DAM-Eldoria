package object;

import main.Entidad;
import main.GamePanel;

public class obj_key extends Entidad {
    GamePanel gp;
    public obj_key(GamePanel gp){
        super(gp);
        name = "Gold Key";
        down1 = setup("/objects/Key01");

    }

}
