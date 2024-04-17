package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum [][];


    public TileManager(GamePanel gp){

        this.gp = gp;

        tile = new Tile[10];

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadmap("/maps/map05.txt");
    }

    public void getTileImage(){

        try{
            ///Cesped
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/001.png"));

            ////Tree
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/002.png"));
            tile[1].colision = true;
            ////Roca
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/003.png"));
            tile[2].colision = true;

            ////Agua
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/004.png"));
            tile[3].colision = true;

            ////Arena
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/005.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadmap(String filepath){

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

                    mapTileNum[col][row] = num;
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

            int tileNum = mapTileNum[Wordlcol][WorldRow];

            int worldX = Wordlcol * gp.tileSize;
            int worldy = WorldRow * gp.tileSize;
            int screenX = worldX - gp.player.wordlx + gp.player.ScreenX;
            int screenY = worldy - gp.player.wordly + gp.player.ScreenY;

            g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize, gp.tileSize, null);

            Wordlcol++;


            if (Wordlcol == gp.maxWorldCol){
                Wordlcol = 0;

                WorldRow++;
            }
        }

    }
}
