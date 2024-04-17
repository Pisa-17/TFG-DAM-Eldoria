package main;

import object.obj_key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

///Clase encargada del HUD
public class OverlayUI {
    GamePanel gp;
    Font arial_20, arial_80B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean juegoFin = false;
    double tiempoJuego;
    DecimalFormat dFormato = new DecimalFormat("#0.00");

    public OverlayUI(GamePanel gp) {
        this.gp = gp;
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        obj_key key = new obj_key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void drawUI(Graphics2D g2) {

        if (juegoFin == true) {
            g2.setFont(arial_20);
            g2.setColor(Color.white);

            String text;
            int textLargo;
            int x;
            int y;

            /// Texto que aparece al terminar el juego cuando abres el cofre con una llave
            text = "Has encontrado el tesoro!";
            textLargo = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLargo / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);


            /// Texto con el tiempo de juego final
            text = "Tu tiempo: " + dFormato.format(tiempoJuego) + " segundos";
            textLargo = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLargo / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 4);
            g2.drawString(text, x, y);

            /// Texto que aparece al terminar el juego
            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Juego terminado!";
            textLargo = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLargo / 2;
            y = gp.screenHeight / 2 - (gp.tileSize);
            g2.drawString(text, x, y);

            gp.gameThread = null;
        }else {
            /// Logica para saber las llaves que tiene el jugador
            g2.setFont(arial_20);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x: " + gp.player.tengoKey, 74, 65);

            /// Tiempo de juego
            tiempoJuego += (double) 1 / 60;
            g2.drawString("Tiempo: " + dFormato.format(tiempoJuego), gp.tileSize*11, 65);

            //Mensaje en pantalla del juego
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30.0f));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }
}
