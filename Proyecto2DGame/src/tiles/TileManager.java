package tiles;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum [][][];


    public TileManager(GamePanel gp){

        this.gp = gp;

        tile = new Tile[50];

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];


        getTileImage();
        loadmap("/maps/mapa1.txt",0);
        loadmap("/maps/mapa2.txt",1);
    }

    public void getTileImage(){
        setup(0,"001",false);
        setup(1,"002",true);
        setup(2,"003",true);
        setup(3,"004",true);
        setup(4,"005",false);
        setup(5,"006",false);
        setup(6,"007",false);
        setup(7,"008",false);
        setup(8,"009",false);
        setup(9,"010",false);
        setup(10,"011",false);
        setup(11,"012",false);
        setup(12,"013",false);
        setup(13,"014",false);
        setup(14,"015",false);
        setup(15,"016",false);
        setup(16,"005",false);
        setup(17,"005",false);
        setup(18,"005",false);
        setup(19,"roca",true);
    }

    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imageName +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].colision = collision;

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadmap(String filepath, int mapNumber){

        try{
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                while (col < gp.maxWorldCol){
                    String numbers [] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[mapNumber][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void draw(Graphics2D g2){

        int Wordlcol = 0;
        int WorldRow = 0;


        while(Wordlcol < gp.maxWorldCol && WorldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[gp.currentMap][Wordlcol][WorldRow];

            int worldX = Wordlcol * gp.tileSize;
            int worldy = WorldRow * gp.tileSize;
            int screenX = worldX - gp.player.wordlx + gp.player.ScreenX;
            int screenY = worldy - gp.player.wordly + gp.player.ScreenY;

            g2.drawImage(tile[tileNum].image,screenX,screenY, null);

            Wordlcol++;


            if (Wordlcol == gp.maxWorldCol){
                Wordlcol = 0;

                WorldRow++;
            }
        }

    }
}
