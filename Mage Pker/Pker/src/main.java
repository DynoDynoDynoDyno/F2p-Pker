import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.wrappers.interactive.Player;
import tasks.AreaTask;
import tasks.FightingTask;
import tasks.RoamingTask;

import static org.dreambot.api.methods.interactive.Players.getLocal;

@ScriptManifest(name = "F2P Mage Pker", description = "My first pk bot", author = "Dyno",
        version = 0.1, category = Category.COMBAT, image = "")
public class main extends AbstractScript {

    private RoamingTask roamingTask;
    private FightingTask fightingTask;
    private AreaTask areaTask;
    private Area roamArea;

    @Override
    public void onStart() {
        roamArea = new Area(3074, 3546, 3097, 3525);
        String foodName = "Trout";

        this.roamingTask = new RoamingTask(roamArea);
        this.fightingTask = new FightingTask(foodName);
        this.areaTask = new AreaTask(roamArea);
    }

    @Override
    public int onLoop() {
        Player localPlayer = getLocal();

        if (localPlayer != null) {
            if (localPlayer.isInCombat()) {
                fightingTask.execute();
                // Roaming task should be paused if the player is in combat
                roamingTask.execute(localPlayer, true);
            } else if (!roamArea.contains(localPlayer)) {
                areaTask.execute(localPlayer);
                // Roaming task should be resumed if the player is not in combat
                roamingTask.execute(localPlayer, false);
            } else {
                roamingTask.execute(localPlayer, false);
            }
        }

        // Return a short sleep time so that the onLoop method runs frequently
        return 50;
    }
}







