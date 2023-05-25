package tasks;

import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.wrappers.interactive.Player;
import java.util.Random;

public class RoamingTask {

    private final Area roamArea;
    private final Random random;

    public RoamingTask(Area roamArea) {
        this.roamArea = roamArea;
        this.random = new Random();
    }

    public void execute(Player localPlayer, boolean isInCombat) {
        // Stop roaming if in combat
        if (isInCombat) {
            return;
        }

        // Check if bot is inside the roamArea
        if (!roamArea.contains(localPlayer)) {
            // If not, walk back to the roamArea
            Walking.walk(roamArea.getRandomTile());
        } else {
            // If yes, walk to a random tile within the roamArea
            Walking.walk(roamArea.getRandomTile());
        }

        // Sleep for a random period between 5 and 30 seconds
        try {
            Thread.sleep((random.nextInt(26) + 5) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

