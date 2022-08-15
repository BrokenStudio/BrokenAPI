package dev.brokenstudio.brokenapi.skull;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import dev.brokenstudio.pinklib.item.Skull;

public class PlayerHeadStorageHandler {

    private final HashMap<UUID, String> textures;

    {
        textures = new HashMap<>();
    }

    public String getTexture(UUID uuid){
        if(textures.containsKey(uuid))
            return textures.get(uuid);
        try {
            String texture = Skull.getTextureUrlByUuid(uuid.toString());
            textures.put(uuid, texture);
            return texture;
        } catch (IOException e) {
            System.err.println("Error while fetching texture from mojang api.");
        }
        return null;
    }

}
