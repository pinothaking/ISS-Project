package game_map_state;

import java.util.List;

public class GameMap {

    private List<Sectors> sectors;

    public GameMap(List<Sectors> sectors) {
        this.sectors = sectors;
    }

    public List<Sectors> getSectors() {
        return sectors;
    }

    public Sectors getSectorByName(String name) {
        return sectors.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    public GameMapTile getTileAt(int globalIndex) {
        // num of tiles per sector
        int tilesPerSector = 10;
        
        int sectorIndex = globalIndex / tilesPerSector; 
        int localIndex = globalIndex % tilesPerSector;  

        if (sectorIndex >= sectors.size()) {
            return null; 
        }

        Sectors sector = sectors.get(sectorIndex);
        return sector.getTiles().get(localIndex);
    }

    public int getTotalSize() {
        return sectors.size() * 10;
    }
}
