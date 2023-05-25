import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.wrappers.interactive.Player;
import tasks.AreaTask;
import tasks.FightingTask;
import tasks.RoamingTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.dreambot.api.methods.interactive.Players.getLocal;

@ScriptManifest(name = "F2P Mage Pker", description = "My first pk bot", author = "Dyno",
        version = 0.1, category = Category.COMBAT, image = "")
public class main extends AbstractScript {

    private RoamingTask roamingTask;
    private FightingTask fightingTask;
    private AreaTask areaTask;
    private Area roamArea;

    private ScheduledExecutorService executorService;

    @Override
    public void onStart() {
        roamArea = new Area(3074, 3546, 3097, 3525);
        String foodName = "Trout";

        this.roamingTask = new RoamingTask(roamArea);
        this.fightingTask = new FightingTask(foodName);
        this.areaTask = new AreaTask(roamArea);

        // Initialize ScheduledExecutorService
        this.executorService = Executors.newScheduledThreadPool(1);
    }

    @Override
    public int onLoop() {
        Player localPlayer = getLocal();

        if (localPlayer != null) {
            if (localPlayer.isInCombat()) {
                fightingTask.execute();
            } else if (!roamArea.contains(localPlayer)) {
                areaTask.execute(localPlayer);
            } else if (!localPlayer.isMoving()) {
                roamingTask.execute(localPlayer, localPlayer.isInCombat());
            }
        }

        // Return a short sleep time so that the onLoop method runs frequently
        return 50;
    }


    @Override
    public void onExit() {
        // Shutdown the executor service when the script stops
        executorService.shutdown();
    }
}








