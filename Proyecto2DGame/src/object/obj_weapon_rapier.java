package object;

import main.Entidad;
import main.GamePanel;

public class obj_weapon_rapier extends Entidad {
    public obj_weapon_rapier(GamePanel gp) {
        super(gp);
        type = type_rapier;
        name = "Rapier";
        down1 = setup("/objects/rapier", gp.tileSize, gp.tileSize);
        attackValue = 7;
        description = "Fast and lethal";
        attackHitbox.width = 36;
        attackHitbox.height = 36;

    }
}
