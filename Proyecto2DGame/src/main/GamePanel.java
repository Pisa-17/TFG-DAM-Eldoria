package main;

import tiles.TileManager;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements  Runnable{
    ///Ajustes de pantalla
    final int tamaniondeltitulo = 16; //// tamanio por defecto para todo en general, ya que sera en 16 bits
    final int escala = 3;

    public final int tileSize = tamaniondeltitulo*escala; ///48x48
    public final int maxscreenColumn = 20;
    public final int maxscreenRow = 12;
    public final int screenWidth = tileSize*maxscreenColumn; ////768 pixels
    public final int screenHeight = tileSize* maxscreenRow; ////576 pixels

    /// FULL SCREEN THINGS
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    ConfigClass config = new ConfigClass(this);


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
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    // ENTITY AND OBJS
    public Player player = new Player(this,KeyH);
    public Entidad obj [] = new Entidad[20];
    public Entidad npc[] = new Entidad[20];
    public Entidad monster[] = new Entidad[20];
    ArrayList<Entidad> entidadList = new ArrayList<>();

    // GAME STATE THINGS
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int statusState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;


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
        aSetter.setMonster();
        playMusic(1);
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn == true){
            setFullScreen();
        }
    }
    public void retry(){
        player.setRetry();
        player.restoreLife();
        aSetter.setNPC();
        aSetter.setMonster();
    }
    public void restart(){
        player.setRetry();
        player.restoreLife();
        aSetter.setNPC();
        aSetter.setMonster();
        player.setItems();
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
                    //repaint();
                    drawToTempScreen();
                    drawToScreen();
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
            for (int i= 0; i<monster.length; i++){
                if (monster[i] != null){
                    if (monster[i].alive == true && monster[i].dying == false){
                        monster[i].update();
                    }
                    if (monster[i].alive == false){
                        monster[i].checkDrop();
                        monster[i] = null;
                    }
                }
            }
        }
        if (gameState == pauseState){
            //nothing at the moment

        }

    }
    public void drawToTempScreen(){
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        //Title screen
        if (gameState == titleState){
            overlayUI.drawUI(g2);
        }
        //Others
        else {
            ///casillas
            TileM.draw(g2);
            //Adicion de entidades a la lista
            entidadList.add(player);
            for (int i = 0; i<npc.length; i++){
                if (npc[i] != null){
                    entidadList.add(npc[i]);
                }
            }
            for (int i=0; i<obj.length; i++){
                if (obj[i]!= null){
                    entidadList.add(obj[i]);
                }
            }
            for (int i=0; i<monster.length; i++){
                if (monster[i]!= null){
                    entidadList.add(monster[i]);
                }
            }
            /// ordenado
            Collections.sort(entidadList, new Comparator<Entidad>() {
                @Override
                public int compare(Entidad o1, Entidad o2) {
                    int result = Integer.compare(o1.wordly, o2.wordlx);
                    return 0;
                }
            });

            //Draw entity
            for (int i = 0; i<entidadList.size(); i++ ){
                entidadList.get(i).draw(g2);
            }

            /// Empty entity list
            entidadList.clear();


            //Overlay UI
            overlayUI.drawUI(g2);

        }
    }

    public void setFullScreen(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.win);

        // Getting the screen width and the screen height
        screenWidth2 = Main.win.getWidth();
        screenHeight2 = Main.win.getHeight();
    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen,0,0,screenWidth2,screenHeight2, null);
        g.dispose();
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
