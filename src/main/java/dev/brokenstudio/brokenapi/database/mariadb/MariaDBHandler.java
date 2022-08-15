package dev.brokenstudio.brokenapi.database.mariadb;

import java.sql.ResultSet;

public class MariaDBHandler {

    private final ConnectionData connectionData;

    public MariaDBHandler(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    public void executePipe(SQLPipe pipe) {
        pipe.execute(new SQLConnection(connectionData.toStrings()));
    }

    public SQLConnection getSQLConnection(){
        return new SQLConnection(connectionData.toStrings());
    }

    public SimpleSQLConnection getSimpleSQLConnection(String table){
        return new SimpleSQLConnection(getSQLConnection(), table);
    }

    public void executeQuery(String query){
        getSQLConnection().query(query);
    }

    public <T> T result(String query, String column, Class<T> classOfT){
        return getSQLConnection().result(query, column, classOfT);
    }

    @Deprecated
    public ResultSet resultSet(String query){
        return getSQLConnection().resultSet(query);
    }

}
