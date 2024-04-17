package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.net.URL;
/// Clase encargada del sonido del juego
public class Sonido {

    Clip clip;
    URL soundURL[] = new URL[30];

    /// Array de canciones del juego
    public Sonido(){
        soundURL[0] = getClass().getResource("/sonido/cancionrusa.wav");
    }
    /// Metodo encargado de cargar el archivo de sonido
    public void setFile(int i){
        try{
            AudioInputStream audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(soundURL[i]);
            clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /// Metodos de reproduccion, detencion y loop del sonido
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
