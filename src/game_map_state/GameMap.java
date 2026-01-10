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
}
