package dev.brokenstudio.brokenapi.language;

import java.util.HashMap;

public class LanguageRepository {

    private final HashMap<String, String> messages;

    {
        messages = new HashMap<>();
    }

    public String get(String key){
        return messages.getOrDefault(key, "§cNo message found for this key.");
    }

    protected void add(String key, String message){
        messages.put(key, message);
    }

}
