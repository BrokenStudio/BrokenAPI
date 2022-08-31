package dev.brokenstudio.brokenapi.location;

import dev.brokenstudio.brokenapi.BrokenAPI;
import dev.brokenstudio.brokenapi.database.mariadb.SQLPipe;
import dev.brokenstudio.brokenapi.serializer.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class LocationAPI {

    private class JsonLocation {

        private final String world;
        private final double x;
        private final double y;
        private final double z;
        private final float yaw;
        private final float pitch;

        public JsonLocation(String world, double x, double y, double z, float yaw, float pitch) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public JsonLocation(Location location){
            this.world = Objects.requireNonNull(location.getWorld()).getName();
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.yaw = location.getYaw();
            this.pitch = location.getPitch();
        }

        public Location toLocation(){
            Location location = new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
            location.setPitch(this.pitch);
            location.setYaw(this.yaw);
            return location;
        }

    }

    private final HashMap<String, Location> locations;
    private final HashMap<String, String> sets;

    {
        locations = new HashMap<>();
        sets = new HashMap<>();
    }

    public void loadLocationSet(String set){
        Connection con = null;
        ResultSet rs = null;
        try {
            con = BrokenAPI.api().getDatabaseHandler().getMariaDBHandler().getSQLConnection().getDriverConnection();
            rs = con.prepareStatement(
                    "SELECT * FROM `api_loc` WHERE `loc_set`='" + set + "';"
            ).executeQuery();
            while(rs.next()){
                locations.put(rs.getString("loc_name"), Serializer.deserialize(rs.getString("loc_data"), JsonLocation.class).toLocation());
                sets.put(rs.getString("loc_name"), set);
            }
        }catch (SQLException ignored){
            ignored.printStackTrace();
        }finally {
            try {
                rs.close();
                con.close();
            }catch (SQLException ig){}
        }
    }

    public void save(){
        CompletableFuture.runAsync(() -> {
            SQLPipe pipe = new SQLPipe();
            locations.forEach((name, location) -> {
                System.out.printf(name);
                pipe.addQuery("INSERT INTO `api_loc` (`loc_name`,`loc_set`,`loc_data`) VALUES " +
                        "('"+name+"','"+sets.get(name)+"','"+Serializer.serialize(new JsonLocation(location))+"')" +
                        " ON DUPLICATE KEY UPDATE `loc_data`='"+Serializer.serialize(new JsonLocation(location))+"';");
            });
            BrokenAPI.api().getDatabaseHandler().getMariaDBHandler().executePipe(pipe);
        });
    }

    public void saveSync(){
        //TEMP
        Bukkit.getLogger().info("Saving Sync");
        SQLPipe pipe = new SQLPipe();
        locations.forEach((name, location) -> {
            Bukkit.getLogger().info(name);
            pipe.addQuery("INSERT INTO `api_loc` (`loc_name`,`loc_set`,`loc_data`) VALUES " +
                    "('"+name+"','"+sets.get(name)+"','"+Serializer.serialize(new JsonLocation(location))+"')" +
                    " ON DUPLICATE KEY UPDATE `loc_data`='"+Serializer.serialize(new JsonLocation(location))+"';");
        });
        BrokenAPI.api().getDatabaseHandler().getMariaDBHandler().executePipe(pipe);
    }

    public Location get(String name){
        return locations.get(name);
    }

    public void set(String set, String name, Location location){
        sets.put(name, set);
        locations.put(name, location);
    }

}
