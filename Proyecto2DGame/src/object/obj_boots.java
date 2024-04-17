package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class obj_boots extends Sobject{
    public obj_boots() {
        name = "Botas";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Scroll01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
