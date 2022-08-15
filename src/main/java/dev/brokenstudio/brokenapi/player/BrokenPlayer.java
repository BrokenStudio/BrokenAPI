package dev.brokenstudio.brokenapi.player;

import dev.brokenstudio.brokenapi.BrokenAPI;
import dev.brokenstudio.brokenapi.serializer.Serializer;

import java.util.HashMap;
import java.util.UUID;

public class BrokenPlayer {

    private final UUID uuid;
    private final HashMap<String, String> properties;

    public BrokenPlayer(UUID uuid){
        this.uuid = uuid;
        this.properties = new HashMap<>();
        setFirstJoined();
        setProperty("languange","ENGLISH");
    }

    public void setProperty(String key, Object value){
        properties.put(key, Serializer.serialize(value));
    }

    public boolean hasProperty(String key){
        return properties.containsKey(key);
    }

    public <T> T getProperty(String key, Class<T> classOf){
        return Serializer.deserialize(properties.get(key), classOf);
    }

    public void setFirstJoined(){
        setProperty("firstJoined", System.currentTimeMillis());
    }

    public void setLastJoined(){
        setProperty("lastJoined", System.currentTimeMillis());
    }

    public void update(){
        BrokenAPI.api().getBrokenPlayerHandler().setBrokenPlayer(this.uuid, this);
    }

}
