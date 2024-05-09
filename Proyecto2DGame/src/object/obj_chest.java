package object;

import main.Entidad;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_chest extends Entidad {
    // Meter collision a true si queremos que se pueda chocar contra el
    GamePanel gp;
    public obj_chest(GamePanel gp){
        super(gp);
        name = "Chest";
        down1 = setup("/objects/Chest0");
        collision = true;
    }
}
