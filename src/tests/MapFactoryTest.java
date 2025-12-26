package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import game_map_state.*;

public class MapFactoryTest {

    private MapFactory factory;
    private GameMap map;

    @BeforeEach
    void setup() {
        factory = new MapFactory();
        map = factory.createMap();
    }

    @Test
    void mapIsCreated() {
        assertNotNull(map);
    }

    @Test
    void sectorsExist() {
        List<Sectors> sectors = map.getSectors();
        assertNotNull(sectors);
        assertFalse(sectors.isEmpty());
    }

    @Test
    void eachSectorHasTiles() {
        for (Sectors s : map.getSectors()) {
            assertNotNull(s.getTiles());
            assertFalse(s.getTiles().isEmpty());
        }
    }

    @Test
    void tileIdsAreUnique() {
        Set<Integer> ids = new HashSet<>();
        for (Sectors s : map.getSectors()) {
            for (GameMapTile t : s.getTiles()) {
                assertTrue(ids.add(t.getId()));
            }
        }
    }

    @Test
    void tilesHaveBehavior() {
        for (Sectors s : map.getSectors()) {
            for (GameMapTile t : s.getTiles()) {
                assertNotNull(t.getTileBehavior());
            }
        }
    }

    @Test
    void noTileHasPlayerInitially() {
        for (Sectors s : map.getSectors()) {
            for (GameMapTile t : s.getTiles()) {
                assertFalse(t.hasPlayer());
            }
        }
    }
}
