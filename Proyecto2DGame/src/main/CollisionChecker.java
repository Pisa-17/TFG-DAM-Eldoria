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

                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.path){
                    case "up":
                        entity.hitbox.y -= entity.speed;
                        if (entity.hitbox.intersects(gp.obj[i].solidArea)){
                            if (gp.obj[i].collision == true){
                                entity.collision = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.hitbox.y += entity.speed;
                        if (entity.hitbox.intersects(gp.obj[i].solidArea)){
                            if (gp.obj[i].collision == true){
                                entity.collision = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.hitbox.x -= entity.speed;
                        if (entity.hitbox.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true){
                                entity.collision = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.hitbox.x += entity.speed;
                        if (entity.hitbox.intersects(gp.obj[i].solidArea)){
                            if (gp.obj[i].collision == true){
                                entity.collision = true;
                            }
                            if (player){
                                index = i;
                            }
                        }
                        break;
                }
                entity.hitbox.x = entity.solidAreaDefaultX;
                entity.hitbox.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;


                }
            }
        return index;

    }
}

