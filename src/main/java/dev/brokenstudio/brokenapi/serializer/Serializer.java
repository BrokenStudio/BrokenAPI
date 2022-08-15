package dev.brokenstudio.brokenapi.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
    Project: FarmersQuest
    File: Serializer
    Created by: Jan Z.
    Created at: 11.08.2022
     
    © 2022 BrokenStudio x Jan
*/

public class Serializer {

    private final static Gson gson = new GsonBuilder().create();
    private final static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    public static String serialize(Object object) {
        return gson.toJson(object);
    }

    public static <T> T deserialize(String string, Class<T> classOfT) {
        return gson.fromJson(string, classOfT);
    }

    public static String prettySerialize(Object object) {
        return prettyGson.toJson(object);
    }
}

