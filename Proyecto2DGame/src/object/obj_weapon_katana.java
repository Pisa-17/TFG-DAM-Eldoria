package object;

import main.Entidad;
import main.GamePanel;

public class obj_weapon_katana extends Entidad {
    public obj_weapon_katana(GamePanel gp) {
        super(gp);

        name = "Katana";
        down1 = setup("/objects/katana");
        attackValue = 3;
    }
}
