package dev.brokenstudio.brokenapi.bootsrapper;

import dev.brokenstudio.brokenapi.BrokenAPI;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordBootstrapper extends Plugin {

    @Override
    public void onEnable() {
        new BrokenAPI(true);
        BrokenAPI.api()._initialize();
    }
}
