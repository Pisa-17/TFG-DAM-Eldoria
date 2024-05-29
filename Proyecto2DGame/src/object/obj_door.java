package object;

import main.Entidad;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_door extends Entidad {
    GamePanel gp;
    public obj_door(GamePanel gp){
        super(gp);
        name = "Door";
        down1 = setup("/objects/Door01", gp.tileSize, gp.tileSize);

    }
}
