package dev.brokenstudio.brokenapi.player;

import dev.brokenstudio.brokenapi.BrokenAPI;
import dev.brokenstudio.brokenapi.player.BrokenPlayer;
import dev.brokenstudio.brokenapi.serializer.Serializer;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class BrokenPlayerHandler {

    public BrokenPlayer getBrokenPlayer(UUID uuid){
        try(Jedis jedis = BrokenAPI.api().getDatabaseHandler().getRedisHandler().getPool().getResource()){
            if(jedis.exists(uuid.toString())){
                return Serializer.deserialize(jedis.get(uuid.toString()), BrokenPlayer.class);
            }else{
                return new BrokenPlayer(uuid);
            }
        }
    }

    protected void setBrokenPlayer(UUID uuid, BrokenPlayer player){
        try(Jedis jedis = BrokenAPI.api().getDatabaseHandler().getRedisHandler().getPool().getResource()){
            jedis.set(uuid.toString(), Serializer.serialize(player));
        }
    }

}
