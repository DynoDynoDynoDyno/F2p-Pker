package tasks;

import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.wrappers.interactive.Player;
import java.util.Random;

public class RoamingTask {

    private final Area roamArea;
    private final Random random;
    private long lastRoamTime;  // Timestamp of the last roam

    public RoamingTask(Area roamArea) {
        this.roamArea = roamArea;
        this.random = new Random();
        this.lastRoamTime = System.currentTimeMillis();
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
            lastRoamTime = System.currentTimeMillis();  // Update the timestamp
        } else {
            // Check if enough time has passed since the last roam (e.g., 30 seconds)
            if (System.currentTimeMillis() - lastRoamTime >= 7 * 1000) {
                // If yes, walk to a random tile within the roamArea
                Walking.walk(roamArea.getRandomTile());
                lastRoamTime = System.currentTimeMillis();  // Update the timestamp
            }
        }
    }
}




