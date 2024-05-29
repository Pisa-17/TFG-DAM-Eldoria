package object;

import main.Entidad;
import main.GamePanel;

public class obj_lifepot extends Entidad {
    GamePanel gp;


    public obj_lifepot(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_consumable;
        name = "Pocion de vida";
        value = 5;
        down1 = setup("/objects/LifePot", gp.tileSize, gp.tileSize);
        description = "Pocion que te recupera vida";
    }
    public void use(Entidad entidad){
        gp.gameState = gp.dialogueState;
        gp.overlayUI.dialogo = "Has bebido la pocion"
                + " Has recuperado " + value + " HP!";
        entidad.life += value;
        if (gp.player.life > gp.player.maxHP){
            gp.player.life = gp.player.maxHP;
        }

    }

}
