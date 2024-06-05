package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
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
        ///OPTIONS STATE
        else if (gp.gameState == gp.optionsState){
            optionStatus(code);
        }
        else if (gp.gameState == gp.gameOverState){
            gameOverState(code);
        }

    }
    public void playerInventory(int code ){
        if (code == KeyEvent.VK_W){
            if (gp.overlayUI.playerslotRow != 0){
                gp.overlayUI.playerslotCol--;
            }
        }
        if (code == KeyEvent.VK_A){
            if (gp.overlayUI.playerslotCol !=0){
                gp.overlayUI.playerslotRow--;
            }
        }
        if (code == KeyEvent.VK_S){
            if (gp.overlayUI.playerslotRow != 3){
                gp.overlayUI.playerslotCol++;
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.overlayUI.playerslotCol != 4){
                gp.overlayUI.playerslotRow++;
            }
        }
    }
    public void npcInventory(int code ){
        if (code == KeyEvent.VK_W){
            if (gp.overlayUI.npcSlotRow != 0){
                gp.overlayUI.npcSlotCol --;
            }
        }
        if (code == KeyEvent.VK_A){
            if (gp.overlayUI.npcSlotCol !=0){
                gp.overlayUI.npcSlotRow --;
            }
        }
        if (code == KeyEvent.VK_S){
            if (gp.overlayUI.npcSlotRow != 3){
                gp.overlayUI.npcSlotCol++;
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.overlayUI.npcSlotCol != 4){
                gp.overlayUI.npcSlotRow ++;
            }
        }
    }

    private void tradeState(int code) {
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if (gp.overlayUI.subState == 0){
            if (code == KeyEvent.VK_W){
                gp.overlayUI.commandNum --;
                if (gp.overlayUI.commandNum < 0){
                    gp.overlayUI.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S){
                gp.overlayUI.commandNum ++;
                if (gp.overlayUI.commandNum > 2){
                    gp.overlayUI.commandNum = 0;
                }
            }
        }
        if (gp.overlayUI.subState == 1){
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE){
                gp.overlayUI.subState = 0;
            }
        }
    }

    private void gameOverState(int code) {
        if (code == KeyEvent.VK_W){
            gp.overlayUI.commandNum--;
            if (gp.overlayUI.commandNum < 0){
                gp.overlayUI.commandNum =1;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.overlayUI.commandNum++;
            if (gp.overlayUI.commandNum < 1){
                gp.overlayUI.commandNum =0;
            }
        }
        if (code == KeyEvent.VK_ENTER){
            if (gp.overlayUI.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if (gp.overlayUI.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }

        }
    }

    private void optionStatus(int code) {
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;

        }
        int maxCommandNum = 0;
        switch (gp.overlayUI.subState){
            case 0:maxCommandNum = 3; break;
            case 3:maxCommandNum = 1; break;
        }
        if (code == KeyEvent.VK_W){
            gp.overlayUI.commandNum--;
            if (gp.overlayUI.commandNum <0){
                gp.overlayUI.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.overlayUI.commandNum++;
            if (gp.overlayUI.commandNum > maxCommandNum){
                gp.overlayUI.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A){
            if (gp.overlayUI.commandNum == 1 && gp.musica.volumeScale > 0){
                gp.musica.volumeScale--;
                gp.musica.checkVol();
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.overlayUI.commandNum == 1 && gp.musica.volumeScale < 5){
                gp.musica.volumeScale++;
                gp.musica.checkVol();
            }
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
            }
            if (gp.overlayUI.commandNum ==1){
                gp.gameState = gp.listState;
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
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionsState;
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

        if (code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
        playerInventory(code);
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
