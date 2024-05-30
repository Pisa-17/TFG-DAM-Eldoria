package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sonido {

    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;
    public Sonido(){
        soundURL[1] = getClass().getResource("/sonido/background.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVol();
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
    public void checkVol(){
        switch (volumeScale){
            case 0: volume = -80f; break;
            case 1: volume = -20f;break;
            case 2: volume = -12f;break;
            case 3: volume = -5f;break;
            case 4: volume = 1f;break;
            case 5: volume = 6f;break;
        }
        fc.setValue(volume);
    }
}
