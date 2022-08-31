package dev.brokenstudio.brokenapi.bootsrapper;

import dev.brokenstudio.brokenapi.BrokenAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotBootstrapper extends JavaPlugin {

    @Override
    public void onEnable() {
        new BrokenAPI(false);
        BrokenAPI.api()._initialize();
    }

    @Override
    public void onDisable() {
        BrokenAPI.api().getLocationAPI().saveSync();
    }
}
