package main;

import object.Sobject;
import tiles.TileManager;

import javax.swing.*;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;

public class GamePanel extends JPanel implements  Runnable{
    ///Ajustes de pantalla
    final int tamaniondeltitulo = 16; //// tamanio por defecto para todo en general, ya que sera en 16 bits
    final int escala = 3;

    public final int tileSize = tamaniondeltitulo*escala; ///48x48
    public final int maxscreenColumn = 16;
    public final int maxscreenRow = 12;
    public final int screenWidth = tileSize*maxscreenColumn; ////768 pixels
    public final int screenHeight = tileSize* maxscreenRow; ////576 pixels

    ////World map Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;


    ///FPS
    int FPS = 60;

    //SYSTEM
    public TileManager TileM = new TileManager(this);
    KeyboardHandler KeyH = new KeyboardHandler(this);
    Sonido musica = new Sonido();
    Sonido soundEffects = new Sonido();

    CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public OverlayUI overlayUI = new OverlayUI(this);
    Thread gameThread;

    // ENTITY AND OBJS
    public Player player = new Player(this,KeyH);
    public Sobject obj [] = new Sobject[10];
    public Entidad npc[] = new Entidad[10];

    // GAME STATE THINGS
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel (){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);
        this.setFocusable(true);
    }
    public void setupStuff(){
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(1);
        gameState = playState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double IntervalDraw =  1000000000/FPS;
        double delta = 0;
        long LastTime = System.nanoTime();
        long CurrenTime;
        long Timer = 0;
        int DrawCount = 0;
            while (gameThread != null){
                CurrenTime = System.nanoTime();
                delta += (CurrenTime - LastTime) / IntervalDraw;
                Timer+=(CurrenTime-LastTime);
                LastTime = CurrenTime;
                if (delta >= 1){
                    update();
                    repaint();
                    delta--;
                    DrawCount++;
                }
                if (Timer >=1000000000){
                    System.out.println("FPS: " + DrawCount);
                    DrawCount = 0;
                    Timer = 0;
                }
            }
    }
    public void update (){
        if (gameState == playState) {

            /// Jugador
            player.update();

            /// NPC things
            for(int i = 0; i<npc.length; i++){
                if (npc[i] != null){
                    npc[i].update();
                }
            }
        }
        if (gameState == pauseState){
            //nothing at the moment
        }

    }
    public void paintComponent(Graphics g){

        super.paintComponents(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        ///casillas
        TileM.draw(g2);
        ///objects
        for (int i = 0; i < obj.length; i++){
            if (obj[i] != null){
                obj[i].draw(g2, this);
            }
        }
        /// npcs
        for(int i = 0; i < npc.length; i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }

        }
        ///player
        player.draw(g2);

        //Overlay UI
        overlayUI.drawUI(g2);
        g2.dispose();
    }
    public void playMusic(int i){
        musica.setFile(i);
        musica.play();
        musica.loop();
    }
    private void stopMusic(){
        musica.stop();
    }
    public void playSE(int i){
        soundEffects.setFile(i);
        soundEffects.play();
    }
}
