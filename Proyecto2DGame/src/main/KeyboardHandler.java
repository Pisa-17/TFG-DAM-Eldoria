package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
///Clase encargada de los controles del juego
public class KeyboardHandler implements KeyListener {
    public boolean upPressed, downPreseed, leftPressed, rightPressed;
    @Override
    public void keyTyped(KeyEvent e) {
    }
    /// Parte referente a las teclas del teclado
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){
            upPressed = true;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S){
            downPreseed = true;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }
    }

    ///Parte encargada del evento del teclado que ocurre al soltarse la tecla
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPreseed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
}
