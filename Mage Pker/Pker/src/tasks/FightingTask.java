package tasks;

import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;

import static org.dreambot.api.utilities.Logger.log;

public class FightingTask {

    private final String foodName;

    public FightingTask(String foodName) {
        this.foodName = foodName;
    }

    public void execute() {
        int currentHPPercent = Combat.getHealthPercent();
        log("Current HP: " + currentHPPercent + "%");

        if (currentHPPercent <= 50) {
            log("HP is 50% or less. Eating " + foodName);
            Inventory.interact(foodName);  // Replace 'foodName' with the name of your food
        }
    }
}
