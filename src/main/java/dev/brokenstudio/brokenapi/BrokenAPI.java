package dev.brokenstudio.brokenapi;

import com.google.gson.GsonBuilder;
import dev.brokenstudio.brokenapi.database.DatabaseHandler;
import dev.brokenstudio.brokenapi.language.LanguageAPI;
import dev.brokenstudio.brokenapi.location.LocationAPI;
import dev.brokenstudio.brokenapi.player.BrokenPlayerHandler;
import dev.brokenstudio.brokenapi.security.Credentials;
import dev.brokenstudio.brokenapi.serializer.Serializer;
import dev.brokenstudio.brokenapi.skull.PlayerHeadStorageHandler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BrokenAPI {

    private static BrokenAPI instance;

    private boolean bungeecord;
    private DatabaseHandler databaseHandler;
    private LanguageAPI languageAPI;
    private BrokenPlayerHandler brokenPlayerHandler;
    private LocationAPI locationAPI;
    private PlayerHeadStorageHandler playerHeadStorageHandler;

    public BrokenAPI(boolean bungeecord){
        this.bungeecord = bungeecord;
        instance = this;
    }

    public void _initialize(){
        initializeDatabaseHandler();
        languageAPI = new LanguageAPI();
        brokenPlayerHandler = new BrokenPlayerHandler();
        if(!bungeecord)locationAPI = new LocationAPI();
        if(!bungeecord)playerHeadStorageHandler = new PlayerHeadStorageHandler();
    }

    private void initializeDatabaseHandler(){
        File folder = new File("plugins/BrokenAPI");
        File file = new File("plugins/BrokenAPI", "sql.json");
        folder.mkdir();
        try {
            if(file.createNewFile()){
                System.err.println("No database credentials supplied to the software.");
                FileWriter writer = new FileWriter(file);
                writer.write(Serializer.prettySerialize(new Credentials("foo","bar")));
                writer.flush();
                writer.close();
                return;
            }
            FileReader reader = new FileReader(file);
            Credentials credentials = new GsonBuilder().create().fromJson(reader, Credentials.class);
            this.databaseHandler = new DatabaseHandler(credentials);
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public LanguageAPI getLanguageAPI() {
        return languageAPI;
    }

    public BrokenPlayerHandler getBrokenPlayerHandler() {
        return brokenPlayerHandler;
    }

    public LocationAPI getLocationAPI() {
        return locationAPI;
    }

    public PlayerHeadStorageHandler getPlayerHeadStorageHandler() {
        return playerHeadStorageHandler;
    }

    public static BrokenAPI api(){
        return instance;
    }

}
