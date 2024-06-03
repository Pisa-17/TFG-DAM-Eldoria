package main;
public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;

    }
    public void checkTitle(Entidad entity){

        int entityLeftWorldX = entity.wordlx + entity.hitbox.x;
        int entityRightWorldX = entity.wordlx + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.wordly + entity.hitbox.y;
        int entityBottomWorldY = entity.wordly + entity.hitbox.y + entity.hitbox.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.path){
            case "up":
                entityTopRow = (entityTopWorldY-entity.speed)/gp.tileSize;
                tileNum1 = gp.TileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.TileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.TileM.tile[tileNum1].colision == true || gp.TileM.tile[tileNum2].colision == true){
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.TileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.TileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.TileM.tile[tileNum1].colision == true || gp.TileM.tile[tileNum2].colision == true){
                    entity.collision = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1 = gp.TileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.TileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.TileM.tile[tileNum1].colision == true || gp.TileM.tile[tileNum2].colision == true){
                    entity.collision = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX+entity.speed)/gp.tileSize;
                tileNum1 = gp.TileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.TileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.TileM.tile[tileNum1].colision == true || gp.TileM.tile[tileNum2].colision == true){
                    entity.collision = true;
                }
                break;
        }
    }
    public int checkObj(Entidad entity, boolean player){
        int index = 999;
        for (int i=0; i<gp.obj[1].length; i++){
            if (gp.obj[gp.currentMap][i] != null){
                entity.hitbox.x = entity.wordlx + entity.hitbox.x;
                entity.hitbox.y = entity.wordly + entity.hitbox.y;

                gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].wordlx + gp.obj[gp.currentMap][i].hitbox.x;
                gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].wordly + gp.obj[gp.currentMap][i].hitbox.y;

                switch (entity.path){
                    case "up":
                        entity.hitbox.y -= entity.speed;
                        break;
                    case "down":
                        entity.hitbox.y += entity.speed;
                        break;
                    case "left":
                        entity.hitbox.x -= entity.speed;
                        break;
                    case "right":
                        entity.hitbox.x += entity.speed;
                        break;
                }
                if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox)){
                    if (gp.obj[gp.currentMap][i].collision == true){
                        entity.collision = true;
                    }
                    if (player){
                        index = i;
                    }
                }
                entity.hitbox.x = entity.solidAreaDefaultX;
                entity.hitbox.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;


                }
            }
        return index;

    }
    public int checkEntity(Entidad entidad, Entidad[][] target){
        int index = 999;
        for (int i=0; i<target[1].length; i++){
            if (target[gp.currentMap][i] != null){
                entidad.hitbox.x = entidad.wordlx + entidad.hitbox.x;
                entidad.hitbox.y = entidad.wordly + entidad.hitbox.y;

                target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].wordlx + target[gp.currentMap][i].hitbox.x;
                target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].wordly + target[gp.currentMap][i].hitbox.y;

                switch (entidad.path){
                    case "up":
                        entidad.hitbox.y -= entidad.speed;
                        break;
                    case "down":
                        entidad.hitbox.y += entidad.speed;
                        break;
                    case "left":
                        entidad.hitbox.x -= entidad.speed;
                        break;
                    case "right":
                        entidad.hitbox.x += entidad.speed;
                        break;
                }
                if (entidad.hitbox.intersects(target[gp.currentMap][i].hitbox)){
                    if (target[gp.currentMap][i] != entidad){
                        entidad.collision = true;
                        index = i;
                    }
                }
                entidad.hitbox.x = entidad.solidAreaDefaultX;
                entidad.hitbox.y = entidad.solidAreaDefaultY;
                target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].solidAreaDefaultY;


            }
        }
        return index;
    }
    public boolean checkPlayer(Entidad entidad){
        boolean contactPlayer = false;
        entidad.hitbox.x = entidad.wordlx + entidad.hitbox.x;
        entidad.hitbox.y = entidad.wordly + entidad.hitbox.y;

        gp.player.hitbox.x = gp.player.wordlx + gp.player.hitbox.x;
        gp.player.hitbox.y = gp.player.wordly + gp.player.hitbox.y;

        switch (entidad.path){
            case "up":
                entidad.hitbox.y -= entidad.speed;
                break;
            case "down":
                entidad.hitbox.y += entidad.speed;
                break;
            case "left":
                entidad.hitbox.x -= entidad.speed;
                break;
            case "right":
                entidad.hitbox.x += entidad.speed;
                break;
        }
        if (entidad.hitbox.intersects(gp.player.hitbox)){
            entidad.collision = true;
            contactPlayer = true;
        }
        entidad.hitbox.x = entidad.solidAreaDefaultX;
        entidad.hitbox.y = entidad.solidAreaDefaultY;
        gp.player.hitbox.x = gp.player.solidAreaDefaultX;
        gp.player.hitbox.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}

