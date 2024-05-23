package object;

import main.Entidad;
import main.GamePanel;

public class obj_defense_scrollFire extends Entidad {

    public obj_defense_scrollFire(GamePanel gp) {
        super(gp);
        type = type_scroll;
        name = "Scroll Fire";
        down1 = setup("/objects/ScrollFire");
        defenseValue = 3;
        description = "A Sacred fire Scroll";
    }
}
