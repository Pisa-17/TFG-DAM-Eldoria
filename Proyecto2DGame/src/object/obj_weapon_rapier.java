package object;

import main.Entidad;
import main.GamePanel;

public class obj_weapon_rapier extends Entidad {
    public obj_weapon_rapier(GamePanel gp) {
        super(gp);

        name = "Rapier";
        down1 = setup("/objects/rapier");
        attackValue = 7;
    }
}
