package game_map_state;

import java.util.*;
import battle_exploration_tiles.*;

public class MapFactory {

    private Random random = new Random();

    public GameMap createMap() {

        Sectors hell = new Sectors("Hell", generateTilesForSector(0));
        Sectors purgatory = new Sectors("Purgatory", generateTilesForSector(1));
        Sectors heaven = new Sectors("Heaven", generateTilesForSector(2));

        return new GameMap(List.of(hell, purgatory, heaven));
    }

    private List<GameMapTile> generateTilesForSector(int sectorIndex) {

        List<GameMapTile> tiles = new ArrayList<>();
        int tilesPerSector = 10;

        for (int i = 0; i < tilesPerSector; i++) {
            Tile behavior = randomTileBehavior();
            int tileId = sectorIndex * 10 + i;

            tiles.add(new GameMapTile(behavior, tileId, false));
        }

        return tiles;
    }

    private Tile randomTileBehavior() {
        int r = random.nextInt(10);

        if (r < 5)
            return new EmptyTile();// 50%
        if (r < 8)
            return new EnemyTile(); // 30%
        // return new ShopTile(); // 20%
        return new EmptyTile();
    }
}
