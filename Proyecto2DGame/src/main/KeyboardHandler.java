package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.IllegalFormatCodePointException;

public class KeyboardHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPreseed, leftPressed, rightPressed, enterPressed;
    @Override
    public void keyTyped(KeyEvent e) {
    }
    public KeyboardHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //Title state
        if (gp.gameState == gp.titleState){
            if (code == KeyEvent.VK_W){
                gp.overlayUI.commandNum--;

                if (gp.overlayUI.commandNum <0){
                    gp.overlayUI.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S){
                gp.overlayUI.commandNum++;
                if (gp.overlayUI.commandNum >2){
                    gp.overlayUI.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER){
                if (gp.overlayUI.commandNum ==0){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if (gp.overlayUI.commandNum ==1){
                    //later on
                }
                if (gp.overlayUI.commandNum ==2){
                    System.exit(0);
                }

            }
        }

        ///Play state
        if (gp.gameState == gp.playState){
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
            if (code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
        }
        ///Pause State
        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }
        ///Dialogue state
        else if (gp.gameState == gp.dialogueState){
            if (code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
    }

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
