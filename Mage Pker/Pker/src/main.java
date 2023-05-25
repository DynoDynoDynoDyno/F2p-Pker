import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.wrappers.interactive.Player;
import tasks.AreaTask;
import tasks.FightingTask;
import tasks.RoamingTask;

import java.awt.*;
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
    private String currentAction = "Starting up...";


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
    public void onPaint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        int y = 40;  // Start y coordinate for text
        int dy = 15; // Amount to increment y coordinate for each line of text
        int x = 10;  // x coordinate for text

        g.drawString("Current HP: " + fightingTask.getCurrentHPPercent() + "%", x, y);
        y += dy;
        Player targetPlayer = fightingTask.getTargetPlayer();
        g.drawString("Target Player: " + (targetPlayer != null ? targetPlayer.getName() : "None"), x, y);
        y += dy;
        g.drawString("Local Player Combat Level: " + fightingTask.getLocalPlayerCombatLevel(), x, y);
        y += dy;
        g.drawString("Wilderness Level: " + fightingTask.getWildernessLevel(), x, y);
    }



    @Override
    public int onLoop() {
        Player localPlayer = getLocal();

        if (localPlayer != null) {
            fightingTask.execute();

            if (localPlayer.isInCombat()) {
                currentAction = "Fighting";
                fightingTask.execute();
            } else if (!roamArea.contains(localPlayer)) {
                currentAction = "Returning to roam area";
                areaTask.execute(localPlayer);
            } else if (!localPlayer.isMoving()) {
                currentAction = "Roaming";
                roamingTask.execute(localPlayer, localPlayer.isInCombat());
            }
        }

        return 50;
    }


    @Override
    public void onExit() {
        // Shutdown the executor service when the script stops
        executorService.shutdown();
    }
}








