package object;

import main.Entidad;
import main.GamePanel;

public class obj_weapon_katana extends Entidad {
    public obj_weapon_katana(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = "Katana";
        down1 = setup("/objects/katana", gp.tileSize, gp.tileSize);
        attackValue = 3;
        description = "A nice katana!";
        attackHitbox.width = 30;
        attackHitbox.height = 30;
        price = 13;
    }
}
