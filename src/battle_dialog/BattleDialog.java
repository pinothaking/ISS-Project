package battle_dialog;

public class BattleDialog {
    public void show(BattleState state){
        System.out.println("Battaglia iniziata!");
        update(state);
    }

    public void update(BattleState state){
        System.out.println(
            "Turno: " + state.getTurn() +
            " - Salute Giocatore: " + state.getPlayerHealth() +
            " - Salute Nemico: " + state.getEnemyHealth()
        );
    }
}
