package tasks;

import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.methods.interactive.Players;

import static org.dreambot.api.utilities.Logger.log;

public class FightingTask {

    private final String foodName;

    public FightingTask(String foodName) {
        this.foodName = foodName;
    }

    public void execute() {
        Player localPlayer = Players.getLocal();
        int localPlayerCombatLevel = localPlayer.getLevel();
        int wildernessLevel = Combat.getWildernessLevel();

        int currentHPPercent = Combat.getHealthPercent();
        log("Current HP: " + currentHPPercent + "%");

        if (currentHPPercent <= 50) {
            log("HP is 50% or less. Eating " + foodName);
            Inventory.interact(foodName);  // Replace 'foodName' with the name of your food
        }

        Player targetPlayer = Players.closest(p -> p != null && !p.equals(localPlayer) &&
                Math.abs(p.getLevel() - localPlayerCombatLevel) <= wildernessLevel &&
                !p.isInCombat());

        if (targetPlayer != null) {
            log("Found player to attack: " + targetPlayer.getName());
            targetPlayer.interact("Attack");
        }
    }
}
