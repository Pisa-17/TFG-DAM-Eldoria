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
                tileNum1 = gp.TileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.TileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.TileM.tile[tileNum1].colision == true || gp.TileM.tile[tileNum2].colision == true){
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.TileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.TileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.TileM.tile[tileNum1].colision == true || gp.TileM.tile[tileNum2].colision == true){
                    entity.collision = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1 = gp.TileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.TileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.TileM.tile[tileNum1].colision == true || gp.TileM.tile[tileNum2].colision == true){
                    entity.collision = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX+entity.speed)/gp.tileSize;
                tileNum1 = gp.TileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.TileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.TileM.tile[tileNum1].colision == true || gp.TileM.tile[tileNum2].colision == true){
                    entity.collision = true;
                }
                break;
        }
    }
    public int checkObj(Entidad entity, boolean player){
        int index = 999;
        for (int i=0; i<gp.obj.length; i++){
            if (gp.obj[i] != null){
                entity.hitbox.x = entity.wordlx + entity.hitbox.x;
                entity.hitbox.y = entity.wordly + entity.hitbox.y;

                gp.obj[i].hitbox.x = gp.obj[i].wordlx + gp.obj[i].hitbox.x;
                gp.obj[i].hitbox.y = gp.obj[i].wordly + gp.obj[i].hitbox.y;

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
                if (entity.hitbox.intersects(gp.obj[i].hitbox)){
                    if (gp.obj[i].collision == true){
                        entity.collision = true;
                    }
                    if (player){
                        index = i;
                    }
                }
                entity.hitbox.x = entity.solidAreaDefaultX;
                entity.hitbox.y = entity.solidAreaDefaultY;
                gp.obj[i].hitbox.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].hitbox.y = gp.obj[i].solidAreaDefaultY;


                }
            }
        return index;

    }
    public int checkEntity(Entidad entidad, Entidad[] target){
        int index = 999;
        for (int i=0; i<target.length; i++){
            if (target[i] != null){
                entidad.hitbox.x = entidad.wordlx + entidad.hitbox.x;
                entidad.hitbox.y = entidad.wordly + entidad.hitbox.y;

                target[i].hitbox.x = target[i].wordlx + target[i].hitbox.x;
                target[i].hitbox.y = target[i].wordly + target[i].hitbox.y;

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
                if (entidad.hitbox.intersects(target[i].hitbox)){
                    if (target[i] != entidad){
                        entidad.collision = true;
                        index = i;
                    }
                }
                entidad.hitbox.x = entidad.solidAreaDefaultX;
                entidad.hitbox.y = entidad.solidAreaDefaultY;
                target[i].hitbox.x = target[i].solidAreaDefaultX;
                target[i].hitbox.y = target[i].solidAreaDefaultY;


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

