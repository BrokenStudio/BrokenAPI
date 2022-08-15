package dev.brokenstudio.brokenapi.database;

import dev.brokenstudio.brokenapi.database.mariadb.ConnectionData;
import dev.brokenstudio.brokenapi.database.mariadb.MariaDBHandler;
import dev.brokenstudio.brokenapi.database.redis.RedisHandler;
import dev.brokenstudio.brokenapi.security.Credentials;

public class DatabaseHandler {

    private RedisHandler redisHandler;
    private MariaDBHandler mariaDBHandler;

    private ConnectionData connectionData;

    public DatabaseHandler(Credentials credentials){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionData = new ConnectionData("localhost", "3306",
                credentials.user(), credentials.password(), "brokendata");
        redisHandler = new RedisHandler();
        mariaDBHandler = new MariaDBHandler(connectionData);
    }

    public ConnectionData getConnectionData() {
        return connectionData;
    }

    public MariaDBHandler getMariaDBHandler() {
        return mariaDBHandler;
    }

    public RedisHandler getRedisHandler() {
        return redisHandler;
    }
}
