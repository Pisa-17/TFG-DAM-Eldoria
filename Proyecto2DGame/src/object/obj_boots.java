package object;

import main.Entidad;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_boots extends Entidad {
    GamePanel gp;
    public obj_boots(GamePanel gp) {
        super(gp);
        name = "Boots";
        down1 = setup("/objects/Scroll01");
    }
}
