package tasks;

import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.methods.interactive.Players;

import static org.dreambot.api.utilities.Logger.log;

public class FightingTask {

    private final String foodName;
    private Player targetPlayer;
    private int currentHPPercent;
    private int localPlayerCombatLevel;
    private int wildernessLevel;

    public FightingTask(String foodName) {
        this.foodName = foodName;
    }

    public void execute() {
        Player localPlayer = Players.getLocal();
        localPlayerCombatLevel = localPlayer.getLevel();
        wildernessLevel = Combat.getWildernessLevel();

        currentHPPercent = Combat.getHealthPercent();
        log("Current HP: " + currentHPPercent + "%");

        if (currentHPPercent <= 50) {
            log("HP is 50% or less. Eating " + foodName);
            Inventory.interact(foodName);  // Replace 'foodName' with the name of your food
        }

        targetPlayer = Players.closest(p -> p != null && !p.equals(localPlayer) &&
                Math.abs(p.getLevel() - localPlayerCombatLevel) <= wildernessLevel &&
                !p.isInCombat());

        if (targetPlayer != null) {
            log("Found player to attack: " + targetPlayer.getName());
            targetPlayer.interact("Attack");
        }
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public int getCurrentHPPercent() {
        return currentHPPercent;
    }

    public int getLocalPlayerCombatLevel() {
        return localPlayerCombatLevel;
    }

    public int getWildernessLevel() {
        return wildernessLevel;
    }
}


