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
            titleState(code);
        }
        ///Play state
        else if (gp.gameState == gp.playState){
            playState(code);
        }
        ///Pause State
        else if (gp.gameState == gp.pauseState){
            pauseState(code);
        }
        ///Dialogue state
        else if (gp.gameState == gp.dialogueState){
            dialogueState(code);
        }
        /// Status state
        else if (gp.gameState == gp.statusState){
            characterStatus(code);
        }
    }
    public void titleState(int code){
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
                //gp.playMusic(0);
            }
            if (gp.overlayUI.commandNum ==1){
                //later on
            }
            if (gp.overlayUI.commandNum ==2){
                System.exit(0);
            }

        }
    }
    public void playState(int code){
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
            if (gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }else if (gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }

        }
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.statusState;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }
    public void pauseState(int code){
        if (code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }
    public void characterStatus(int code){
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
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
