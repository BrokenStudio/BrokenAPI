package dev.brokenstudio.brokenapi.database.mariadb;

import dev.brokenstudio.brokenapi.serializer.Serializer;

public class SimpleSQLConnection {

    private SQLConnection connection;
    private SQLPipe pipe;
    private final String table;

    public SimpleSQLConnection(SQLConnection connection, String table) {
        this.connection = connection;
        pipe = new SQLPipe();
        this.table = table;
    }

    public void set(String key, Object value, String... conditions) {
        pipe.addQuery("UPDATE `"+table+"` SET `"+key+"`='"+ Serializer.serialize(value) +"' WHERE "+conditions+";");
    }

    public void insert(String[] keys, Object[] values){
        String query = "INSERT INTO `"+table+"` (";
        for(String key : keys){
            query += "`"+key+"`,";
        }
        query = query.substring(0, query.length()-1);
        query += ") VALUES (";
        for(Object value : values){
            query += "'"+Serializer.serialize(value)+"',";
        }
        query = query.substring(0, query.length()-1);
        query += ");";
        pipe.addQuery(query);
    }

    public void insert(String key, Object value){
        insert(new String[]{key}, new Object[]{value});
    }

    /**
     * Is fired immediately.
     * @param key The Column to get.
     * @param classOfT The Class of the Object to get.
     * @param conditions The Conditions to get the Object.
     * @return The Object
     * @param <T> The Type of the Object to get.
     */
    public <T> T get(String key, Class<T> classOfT, String... conditions){
        String query = "SELECT `"+key+"` FROM `"+table+"` WHERE "+conditions+";";
        return connection.result(query, key, classOfT);
    }

    public void executePipeline(){
        pipe.execute(connection);
    }

}
