package tasks;

import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.wrappers.interactive.Player;

import static org.dreambot.api.utilities.Logger.log;

public class AreaTask {

    private final Area roamArea;

    public AreaTask(Area roamArea) {
        this.roamArea = roamArea;
    }

    public void execute(Player localPlayer) {
        // Check if bot is inside the roamArea and not currently walking
        if (!roamArea.contains(localPlayer) && !localPlayer.isMoving()) {
            // If not, walk back to the roamArea
            Walking.walk(roamArea.getRandomTile());
            log("Not in roam area. Walking back...");
        }
    }
}



