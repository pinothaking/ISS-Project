package battle_exploration_tiles;

import Character.Enemy.Enemy;
import game_map_state.GameMapTile;

public class EnemyTile implements Tile {

    private Enemy enemy;
    private Realm realm;
    private GameMapTile mapTile;
    
    public EnemyTile() {
        //di default il nemico è null
    }

    public EnemyTile(Realm realm, GameMapTile mapTile) {
        this.realm = realm;
        this.mapTile = mapTile;
    }

    public EnemyTile(Enemy enemy) {
        this.enemy = enemy;
    }

    // Setter e getter
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }
    public Realm setRealm() {
        if (mapTile.getId() < 15 && mapTile.getId() >=0) {
            return realm = Realm.INFERNO;
        }else if (mapTile.getId() >=15 && mapTile.getId() <30) {
            return realm = Realm.PURGATORIO;
        }else if (mapTile.getId() >=30 && mapTile.getId() <=45) {
            return realm = Realm.PARADISO;
        }
        return realm;
    }

    @Override
    public void interact(GameContext context) {
        if (enemy != null) {
            System.out.println("C'è un nemico nella tua casella! Tocca combattere bro");
            context.setState(new BattleState());
        } else {
            System.out.println("Casella vuota.");
        }
    }
}
