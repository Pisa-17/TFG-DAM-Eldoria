package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sonido {

    Clip clip;
    URL soundURL[] = new URL[30];
    public Sonido(){
        soundURL[0] = getClass().getResource("/sonido/cancionrusa.wav");
        soundURL[1] = getClass().getResource("/sonido/background.wav");
        soundURL[2] = getClass().getResource("/sonido/CoinEffect.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(soundURL[i]);
            clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
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
