package tasks;

import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;

public class FightingTask {

    private final String foodName;

    public FightingTask(String foodName) {
        this.foodName = foodName;
    }

    public void execute() {
        int currentHPPercent = Combat.getHealthPercent();

        if (currentHPPercent <= 50) {
            Inventory.interact(foodName);  // Replace 'foodName' with the name of your food
        }
    }
}
