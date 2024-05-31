package main;

import java.io.*;

public class ConfigClass {
    GamePanel gp;

    public ConfigClass(GamePanel gp){
        this.gp = gp;
    }
    public void saveConfig(){
            try{
                BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
                if (gp.fullScreenOn = true){
                    bw.write("On");
                }
                if (gp.fullScreenOn = false){
                    bw.write("Off");
                }
                bw.newLine();
                bw.write(String.valueOf(gp.musica.volumeScale));
                bw.newLine();

                bw.close();
            }catch (IOException e){
                e.printStackTrace();
            }
    }
    public void loadConfig(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            if (s.equals("On")){
                gp.fullScreenOn = true;
            }
            if (s.equals("Off")){
                gp.fullScreenOn = false;
            }

            s = br.readLine();
            gp.musica.volumeScale = Integer.parseInt(s);

            br.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
