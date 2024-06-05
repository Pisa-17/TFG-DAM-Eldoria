package object;

import main.Entidad;
import main.GamePanel;

public class obj_heart extends Entidad {
GamePanel gp;
    public obj_heart(GamePanel gp){
        super(gp);
        this.gp = gp;
        //type = type_pickupOnly;
        name= "Heart";
        value = 2;
        down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_empty", gp.tileSize, gp.tileSize);
        price = 5;
    }
    public void use(Entidad entidad){
        gp.overlayUI.addMessage("Vida + " +value);
        entidad.life += value;
    }



}
