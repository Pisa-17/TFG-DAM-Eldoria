package object;

import main.Entidad;
import main.GamePanel;

public class obj_coin extends Entidad {
    GamePanel gp;
    public obj_coin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = "Gold Coin";
        value = 1;
        down1 = setup("/objects/GoldCoin", gp.tileSize, gp.tileSize);
    }

    public void use(Entidad entidad){
        gp.overlayUI.addMessage("Coin + " + value);
    }
}
