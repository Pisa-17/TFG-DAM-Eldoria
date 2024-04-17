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
    KeyboardHandler KeyH = new KeyboardHandler();
    /// Variables para el sonido
    Sonido musica = new Sonido();
    Sonido soundEffects = new Sonido();
    /// Variables para la colision o hitbox

    CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public OverlayUI overlayUI = new OverlayUI(this);
    /// Thread principal
    Thread gameThread;
    /// Declaracion de objetos jugador y objetos del mapa
    public Player player = new Player(this,KeyH);
    public Sobject obj [] = new Sobject[10];
    /// Constructor de la clase que nos realiza el panel principal del programa

    public GamePanel (){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);
        this.setFocusable(true);
    }
    /// Metodo que nos permite inicializar los objetos del juego y los coloca en el mapa
    public void setupStuff(){
        aSetter.setObject();
        playMusic(1);
    }
    /// Metodo que nos permite iniciar el hilo principal del juego
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    /// Metodo para tener el juego a 60 fps
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
    /// Metodo que nos actualiza la posicion del jugador constantemente
    public void update (){
       player.update();
    }
    /// Metodo que nos pinta los objetos en el mapa
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
        ///player
        player.draw(g2);

        //Overlay UI
        overlayUI.drawUI(g2);
        g2.dispose();
    }
    /// Metodo para poner musica
    public void playMusic(int i){
        musica.setFile(i);
        musica.play();
        musica.loop();
    }
    /// Metodo para parar la musica
    public void stopMusic(){
        musica.stop();
    }
    /// Metodo para poner efectos de sonido ¨ACTUALMENTE EN DESHUSO¨
    public void playSE(int i){
        soundEffects.setFile(i);
        soundEffects.play();
    }
}
